package org.airlineticket_idea.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.airlineticket_idea.mapper.AirlineMapper;
import org.airlineticket_idea.mapper.OrderMapper;
import org.airlineticket_idea.pojo.Customer;
import org.airlineticket_idea.pojo.Order;
import org.airlineticket_idea.pojo.vo.AirlineVO;
import org.airlineticket_idea.pojo.vo.PageKeywords;
import org.airlineticket_idea.service.CustomerService;
import org.airlineticket_idea.mapper.CustomerMapper;
import org.airlineticket_idea.utils.JwtHelper;
import org.airlineticket_idea.utils.MD5Util;
import org.airlineticket_idea.utils.Result;
import org.airlineticket_idea.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 35461
 * @description 针对表【customer】的数据库操作Service实现
 * @createDate 2025-05-05 01:45:29
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer>
        implements CustomerService {
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private AirlineMapper airlineMapper;

    @Autowired
    private JwtHelper jwtHelper;

    @Override
    public Result register(Customer customer) {
        LambdaQueryWrapper<Customer> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Customer::getPhone, customer.getPhone());
        long count = customerMapper.selectCount(queryWrapper);
        if (count > 0) {
            return Result.build(null, ResultCodeEnum.USERNAME_USED);
        }
        customer.setPassword(MD5Util.encrypt(customer.getPassword()));
        customer.setMilsPoints(-1);
        customerMapper.insert(customer);
        return Result.ok(null);
    }

    @Override
    public Result login(Customer customer) {
        LambdaQueryWrapper<Customer> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Customer::getPhone, customer.getPhone());
        Customer loginCustomer = customerMapper.selectOne(queryWrapper);
        if (loginCustomer == null) {
            return Result.build(null, ResultCodeEnum.USERNAME_ERROR);
        }
        if (!StringUtils.isEmpty(customer.getPassword()) && MD5Util.encrypt(customer.getPassword()).equals(loginCustomer.getPassword())) {
            String token = jwtHelper.createToken(Long.valueOf(loginCustomer.getCustomerId()));
            Map data = new HashMap();
            data.put("token", token);
            return Result.ok(data);
        } else {
            return Result.build(null, ResultCodeEnum.PASSWORD_ERROR);
        }
    }

    @Override
    public Result getCustomerAirline(PageKeywords pageKeywords) {
        IPage<Map> page = new Page<>(pageKeywords.getPageNum(), pageKeywords.getPageSize());
        QueryWrapper<AirlineVO> queryWrapper = new QueryWrapper<>();
        if (pageKeywords.getDepartureKeyword() != null && pageKeywords.getDepartureKeyword().length() > 0) {
            queryWrapper.like("a.departure", pageKeywords.getDepartureKeyword());
        }
        if (pageKeywords.getArrivalKeyword() != null && pageKeywords.getArrivalKeyword().length() > 0) {
            queryWrapper.like("a.arrival", pageKeywords.getArrivalKeyword());
        }
        if (pageKeywords.getDateKeyword() != null) {
            queryWrapper.eq("a.date", pageKeywords.getDateKeyword());
        }
        // 排除起飞时间距离当前时间不足3小时的航班
        queryWrapper.and(wrapper -> wrapper.eq("a.date", LocalDate.now())
                .gt("a.departure_time", LocalTime.now().plusHours(3))
                .or()
                .gt("a.date", LocalDate.now())
        );
        // 排序
        queryWrapper.orderByAsc("a.date", "a.departure_time");
        airlineMapper.selectAirlineListWithPage(page, pageKeywords, queryWrapper);
        List<Map> record = page.getRecords();
        Map data = new HashMap();
        data.put("pageData", record);
        data.put("pageNum", page.getCurrent());
        data.put("pageSize", page.getSize());
        data.put("totalPage", page.getPages());
        data.put("totalSize", page.getTotal());
        return Result.ok(data);

    }

    @Override
    public Result getUserInfo(String token) {
        boolean expiration = jwtHelper.isExpiration(token);
        if (expiration) {
            return Result.build(null, ResultCodeEnum.NOTLOGIN);
        }
        int userId = jwtHelper.getUserId(token).intValue();
        Customer data = customerMapper.selectById(userId);
        data.setPassword("******");
        return Result.ok(data);
    }

    @Override
    public Result modifyUserInfo(String token, Customer customer) {
        boolean expiration = jwtHelper.isExpiration(token);
        if (expiration) {
            return Result.build(null, ResultCodeEnum.NOTLOGIN);
        }
        int userId = jwtHelper.getUserId(token).intValue();
        Customer data = customerMapper.selectById(userId);
        data.setEmail(customer.getEmail());
        data.setUserName(customer.getUserName());
        customerMapper.updateById(data);
        return Result.ok(null);
    }


}

