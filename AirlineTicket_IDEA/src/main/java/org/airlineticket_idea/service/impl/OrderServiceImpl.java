package org.airlineticket_idea.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import org.airlineticket_idea.pojo.dto.AirlineKeywords;
import org.airlineticket_idea.pojo.dto.OrderDTO;
import org.airlineticket_idea.pojo.dto.OrderKeywords;
import org.airlineticket_idea.pojo.vo.AirlineVO;
import org.airlineticket_idea.pojo.vo.ShowAirlineStatVO;
import org.airlineticket_idea.service.OrderService;
import org.airlineticket_idea.utils.JwtHelper;
import org.airlineticket_idea.utils.Result;
import org.airlineticket_idea.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    public Result getHistoryOrder(String token, AirlineKeywords airlineKeywords) {
        // 1. Token 验证
        if (jwtHelper.isExpiration(token)) {
            return Result.build(null, ResultCodeEnum.NOTLOGIN);
        }

        // 2. 构建查询条件
        int userId = jwtHelper.getUserId(token).intValue();
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("o.customer_id", userId);

        // 3. 执行分页查询（关键修改：接收返回值）
        IPage<Map<String, Object>> page = new Page<>(airlineKeywords.getPageNum(), airlineKeywords.getPageSize());
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
    public Result calculatePrice(String token, AirlineVO airlineVO) {
        int userId = jwtHelper.getUserId(token).intValue();
        int points = customerMapper.selectById(userId).getMilsPoints();
        airlineVO.setFirstSeatDiscountPrice(getDiscountPrice(points, airlineVO.getFirstSeatPrice()));
        airlineVO.setSecondSeatDiscountPrice(getDiscountPrice(points, airlineVO.getSecondSeatPrice()));
        airlineVO.setThirdSeatDiscountPrice(getDiscountPrice(points, airlineVO.getThirdSeatPrice()));
        airlineVO.setFirstSeatDiscount(airlineVO.getFirstSeatPrice().subtract(airlineVO.getFirstSeatDiscountPrice()));
        airlineVO.setSecondSeatDiscount(airlineVO.getSecondSeatPrice().subtract(airlineVO.getSecondSeatDiscountPrice()));
        airlineVO.setThirdSeatDiscount(airlineVO.getThirdSeatPrice().subtract(airlineVO.getThirdSeatDiscountPrice()));
        System.out.println(airlineVO);
        return Result.ok(airlineVO);

    }

    @Override
    public Result showAirlineStat() {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("airline_id,COUNT(*) as order_count")
                .groupBy("airline_id")
                .orderByDesc("order_count")
                .last("LIMIT 5");
        List<Map<String, Object>> airlineOrderCounts = orderMapper.selectMaps(queryWrapper);

        List<ShowAirlineStatVO> voList = new ArrayList<>();

        for (Map<String, Object> map : airlineOrderCounts) {
            Integer airlineId = (Integer) map.get("airline_id");
            Long orderCount = (Long) map.get("order_count");
            Airline airline = airlineMapper.selectById(airlineId);
            if (airline == null) {
                continue;
            }
            ShowAirlineStatVO vo = new ShowAirlineStatVO();
            vo.setAirlineId(airline.getAirlineId());
            vo.setAirlineDepartureCity(airline.getDeparture());
            vo.setAirlineArrivalCity(airline.getArrival());
            vo.setAirlineOrderCount(String.valueOf(orderCount));
            voList.add(vo);
        }
        System.out.println(voList);
        return Result.ok(voList);
    }

    @Override
    public Result getOrders(OrderKeywords orderKeywords, String token) {
        IPage<Map<String, Object>> page = new Page<>(orderKeywords.getPageNum(), orderKeywords.getPageSize());
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>(); // 改为 QueryWrapper

        if (orderKeywords.getOrderId() != null) {
            queryWrapper.eq("o.order_id", orderKeywords.getOrderId());
        }
        if (orderKeywords.getCustomerId() != null && !orderKeywords.getCustomerId().isEmpty()) {
            queryWrapper.eq("o.customer_id", orderKeywords.getCustomerId());
        }
        if (orderKeywords.getCitizenName() != null && !orderKeywords.getCitizenName().isEmpty()) {
            queryWrapper.like("o.citizen_name", orderKeywords.getCitizenName());
        }
        if (orderKeywords.getAirlineId() != null && !orderKeywords.getAirlineId().isEmpty()) {
            queryWrapper.eq("o.airline_id", orderKeywords.getAirlineId());
        }
        queryWrapper.orderByDesc("o.order_id");

        IPage<Map<String, Object>> resultPage = orderMapper.selectOrderListWithPage(page, queryWrapper);

        return Result.ok(Map.of(
                "pageData", resultPage.getRecords(),
                "pageNum", resultPage.getCurrent(),
                "pageSize", resultPage.getSize(),
                "totalPage", resultPage.getPages(),
                "totalSize", resultPage.getTotal()
        ));
    }

    @Override
    public Result updateOrder(Order order) {
        orderMapper.updateById(order);
        return Result.build(null, 200, "订单更新成功");
    }

    //获取经过用户积分折扣后优惠的价格
    public BigDecimal getDiscountPrice(int points, BigDecimal price) {
        int maxDiscount = price.multiply(BigDecimal.valueOf(0.2)).intValue();
        int discount = points / 100;
        if (points > -1) {
            if (discount > maxDiscount) {
                price = price.subtract(BigDecimal.valueOf(maxDiscount));
            } else if (discount >= 10 && discount < maxDiscount) {
                price = price.subtract(BigDecimal.valueOf(discount));
            }
        }
        return price;
    }

    @Override
    @Transactional
    public Result buyTicket(String token, OrderDTO orderDTO) {
        //前端往order传的值有airlineId,seatType,citizenId,citizenName,spending;
        if (jwtHelper.isExpiration(token)) {
            return Result.build(null, ResultCodeEnum.NOTLOGIN);
        }
        Order order = orderDTO.toOrder();
        int userId = jwtHelper.getUserId(token).intValue();
        order.setCustomerId(userId);
        //设置为现在的的日期时间
        order.setBookingTime(LocalDateTime.now());
        //todo 后续需要根据用户选择的航空公司和座位类型设置价格以及位置的安排
        Airline airline = airlineMapper.selectById(order.getAirlineId());
        Airplane airplane = airplaneMapper.selectById(airline.getAirplaneId());
        //根据座位类型设置价格和座位号
        if (order.getSeatType().equals("头等舱")) {
            airline.setFirstSeat(airline.getFirstSeat() - 1);
            int seatNum = airplane.getFirstSeat() - airline.getFirstSeat();
            order.setSeatId(getSeatId(seatNum, "A"));
        } else if (order.getSeatType().equals("商务舱")) {
            airline.setSecondSeat(airline.getSecondSeat() - 1);
            int seatNum = airplane.getSecondSeat() - airline.getSecondSeat();
            order.setSeatId(getSeatId(seatNum, "B"));
        } else if (order.getSeatType().equals("经济舱")) {
            airline.setThirdSeat(airline.getThirdSeat() - 1);
            int seatNum = airplane.getThirdSeat() - airline.getThirdSeat();
            order.setSeatId(getSeatId(seatNum, "C"));
        }
        airlineMapper.updateById(airline);
        //todo 在下面来设置折扣并获取折扣后的价格并进行用户积分的计算
        Customer customer = customerMapper.selectById(userId);
        int points = customer.getMilsPoints();
        //折扣最多打8折，每100点积分可以抵扣1元
        //这里先检查是否是会员
        if (points > -1) {
//下面检测用户是否选择了进行折扣
            if (orderDTO.isUseDiscount()) {
                customer.setMilsPoints(points - orderDTO.getDiscount().intValue() * 100);
            } else if (orderDTO.isUseDiscount()) {
                //客户选择不使用折扣，进行积分的添加，添加的积分和本次消费的积分相等
                customer.setMilsPoints(points + order.getSpending().intValue());
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
        int userId = jwtHelper.getUserId(token).intValue();

        Order newOrder = orderMapper.selectById(order.getOrderId());

        newOrder.setOrderStat("已退票");

        orderMapper.update(newOrder, new LambdaQueryWrapper<Order>().eq(Order::getOrderId, order.getOrderId()));
        //todo 退票后需要将用户积分扣除
        Customer customer = customerMapper.selectById(userId);
        int point = customer.getMilsPoints();
        int refundPoint = newOrder.getSpending().intValue();
        if (point != -1) {
            if (point <= refundPoint) {
                point = 0;
            } else if (point > refundPoint) {
                point -= refundPoint;
            }
            customer.setMilsPoints(point);
            customerMapper.updateById(customer);
        }
        return Result.build(null, 200, "退票成功");
    }

    public String getSeatId(int seatNum, String seatType) {
        char word = (char) (seatNum % 10 + 'A');
        int num = (seatNum / 10) + 1;
        String seatId = seatType + word + num;
        return seatId;
    }

    @Override
    public Result memberRegister(String token) {
        if (jwtHelper.isExpiration(token)) {
            return Result.build(null, ResultCodeEnum.NOTLOGIN);
        }
        int userId = jwtHelper.getUserId(token).intValue();
        Customer customer = customerMapper.selectById(userId);
        customer.setMilsPoints(0);
        customerMapper.updateById(customer);
        return Result.build(null, 200, "会员注册成功");
    }


}




