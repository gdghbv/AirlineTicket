package org.airlineticket_idea.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.airlineticket_idea.mapper.AirlineMapper;
import org.airlineticket_idea.mapper.AirplaneMapper;
import org.airlineticket_idea.mapper.CustomerMapper;
import org.airlineticket_idea.mapper.OrderMapper;
import org.airlineticket_idea.pojo.Airline;
import org.airlineticket_idea.pojo.Airplane;
import org.airlineticket_idea.pojo.Customer;
import org.airlineticket_idea.pojo.Order;
import org.airlineticket_idea.pojo.vo.PageKeywords;
import org.airlineticket_idea.service.OrderService;
import org.airlineticket_idea.utils.JwtHelper;
import org.airlineticket_idea.utils.Result;
import org.airlineticket_idea.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author 35461
 * @description 针对表【order】的数据库操作Service实现
 * @createDate 2025-05-05 23:50:51
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
        implements OrderService {
    @Autowired
    JwtHelper jwtHelper;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    AirlineMapper airlineMapper;
    @Autowired
    CustomerMapper customerMapper;
    @Autowired
    AirplaneMapper airplaneMapper;

    @Override
    public Result getHistoryOrder(String token, PageKeywords pageKeywords) {
        // 1. Token 验证
        if (jwtHelper.isExpiration(token)) {
            return Result.build(null, ResultCodeEnum.NOTLOGIN);
        }

        // 2. 构建查询条件
        String userId = jwtHelper.getUserId(token);
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Order::getCustomerId, userId);

        // 3. 执行分页查询（关键修改：接收返回值）
        IPage<Map<String, Object>> page = new Page<>(pageKeywords.getPageNum(), pageKeywords.getPageSize());
        IPage<Map<String, Object>> resultPage = orderMapper.selectOrderListWithPage(page, queryWrapper);

        // 4. 返回结果
        return Result.ok(Map.of(
                "pageData", resultPage.getRecords(),
                "pageNum", resultPage.getCurrent(),
                "pageSize", resultPage.getSize(),
                "totalPage", resultPage.getPages(),
                "totalSize", resultPage.getTotal()
        ));
    }

    @Override
    public Result buyTicket(String token, Order order,boolean useDiscount) {
        //前端往order传的值有airlineId,seatType,citizenId,citizenName;
        if (jwtHelper.isExpiration(token)) {
            return Result.build(null, ResultCodeEnum.NOTLOGIN);
        }
        String userId = jwtHelper.getUserId(token);
        order.setCustomerId(userId);
        //设置为现在的的日期时间
        order.setBookingTime(LocalDateTime.now());
        //todo 后续需要根据用户选择的航空公司和座位类型设置价格以及位置的安排
        Airline airline = airlineMapper.selectById(order.getAirlineId());
        Airplane airplane = airplaneMapper.selectById(airline.getAirplaneId());
        //根据座位类型设置价格和座位号，还需要根据用户的积分来设置折扣
        order.setSpending(airline.getPrice());
        if (order.getSeatType().equals("头等舱")) {
            airline.setFirstSeat(airline.getFirstSeat()-1);
            order.setSpending(order.getSpending().multiply(BigDecimal.valueOf(1.5)));
            int seatNum=airplane.getFirstSeat()-airline.getFirstSeat();
            order.setSeatId(getSeatId(seatNum,"A"));
        } else if (order.getSeatType().equals("商务舱")) {
            airline.setSecondSeat(airline.getSecondSeat()-1);
            order.setSpending(order.getSpending().multiply(BigDecimal.valueOf(1.2)));
            int seatNum=airplane.getSecondSeat()-airline.getSecondSeat();
            order.setSeatId(getSeatId(seatNum,"B"));
        } else if (order.getSeatType().equals("经济舱")) {
            airline.setThirdSeat(airline.getThirdSeat()-1);
            order.setSpending(order.getSpending().multiply(BigDecimal.valueOf(1.0)));
            int seatNum=airplane.getThirdSeat()-airline.getThirdSeat();
            order.setSeatId(getSeatId(seatNum,"C"));
        }
        //todo 在下面来设置折扣并获取折扣后的价格并进行用户积分的计算
        Customer customer = customerMapper.selectById(userId);
        int points=customer.getMilsPoints();
        //折扣最多打8折，每100点积分可以抵扣1元
        int discount=points/100;
        int maxDiscount=order.getSpending().multiply(BigDecimal.valueOf(0.2)).intValue();
        //这里先检查是否是会员
        if(points>-1){
//下面检测用户是否选择了进行折扣
if (useDiscount){
    //用户选择使用折扣，进行积分的抵扣,折扣最少要扣减10元
    if(discount>10&&discount<=maxDiscount){
        order.setSpending(order.getSpending().subtract(BigDecimal.valueOf(discount)));
        customer.setMilsPoints(points-discount*100);
    } else if (discount>maxDiscount) {
        order.setSpending(order.getSpending().subtract(BigDecimal.valueOf(maxDiscount)));
        customer.setMilsPoints(points-maxDiscount*100);
    }
} else if (useDiscount!=true) {
    //客户选择不使用折扣，进行积分的添加，添加的积分和本次消费的积分相等
    customer.setMilsPoints(points+order.getSpending().intValue());
}
customerMapper.updateById(customer);
        }
        //设置订单状态
        order.setOrderStat("已支付");
        orderMapper.insert(order);
//可以修改为枚举类
        return Result.build(null, 200, "购买成功");
    }

    @Override
    public Result refundTicket(String token, Order order) {
        if (jwtHelper.isExpiration(token)) {
            return Result.build(null, ResultCodeEnum.NOTLOGIN);
        }
        String userId = jwtHelper.getUserId(token);

        Order newOrder=orderMapper.selectById(order.getOrderId());

        newOrder.setOrderStat("已退票");

        orderMapper.update(newOrder,new LambdaQueryWrapper<Order>().eq(Order::getOrderId,order.getOrderId()));
        //todo 退票后需要将用户积分扣除
        Customer customer=customerMapper.selectById(userId);
        int point=customer.getMilsPoints();
        int refundPoint=newOrder.getSpending().intValue();
        if(point!=-1){
            if(point<=refundPoint){
                point=0;
            } else if (point>refundPoint) {
                point-=refundPoint;
            }
            customer.setMilsPoints(point);
            customerMapper.updateById(customer);
        }
        return Result.build(null, 200, "退票成功");
    }
    public String getSeatId(int seatNum,String seatType){
        char word=(char)(seatNum%10+'A');
        int num=(seatNum/10)+1;
        String seatId=seatType+word+num;
        return seatId;
    }
}




