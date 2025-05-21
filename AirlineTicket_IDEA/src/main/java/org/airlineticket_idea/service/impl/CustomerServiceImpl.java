package org.airlineticket_idea.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.airlineticket_idea.mapper.AirlineMapper;
import org.airlineticket_idea.pojo.Customer;
import org.airlineticket_idea.pojo.dto.UserKeywords;
import org.airlineticket_idea.service.CustomerService;
import org.airlineticket_idea.mapper.CustomerMapper;
import org.airlineticket_idea.utils.JwtHelper;
import org.airlineticket_idea.utils.MD5Util;
import org.airlineticket_idea.utils.Result;
import org.airlineticket_idea.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private JwtHelper jwtHelper;

    @Override
    public Result register(Customer customer) {
        LambdaQueryWrapper<Customer> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Customer::getPhone, customer.getPhone());
        long count = customerMapper.selectCount(queryWrapper);
        if (count > 0) {
            return Result.build(null, ResultCodeEnum.PHONE_USED);
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
            return Result.build(null, ResultCodeEnum.PHONE_ERROR);
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

    @Override
    public Result users(UserKeywords userKeywords) {
        IPage<Map> page = new Page<>(userKeywords.getPageNum(), userKeywords.getPageSize());
        LambdaQueryWrapper<Customer> queryWrapper = new LambdaQueryWrapper<>();
        if (userKeywords.getEmailKeywords() != null && userKeywords.getEmailKeywords().length() > 0) {
           queryWrapper.like(Customer::getEmail, userKeywords.getEmailKeywords());
        }
        if (userKeywords.getPhoneKeywords() != null && userKeywords.getPhoneKeywords().length() > 0) {
             queryWrapper.like(Customer::getPhone, userKeywords.getPhoneKeywords());
        }
        if (userKeywords.getIdKeywords() != null && userKeywords.getIdKeywords() > 0) {
             queryWrapper.eq(Customer::getCustomerId, userKeywords.getIdKeywords());
        }
        if (userKeywords.getNameKeywords() != null && userKeywords.getNameKeywords().length() > 0) {
                queryWrapper.like(Customer::getUserName, userKeywords.getNameKeywords());
        }
        customerMapper.selectListWithPageAndSelector(page,  queryWrapper);
        List<Map> records = page.getRecords();
        Map data = new HashMap();
        data.put("pageData", records);
        data.put("pageNum", page.getPages());
        data.put("totalPage", page.getPages());
        data.put("totalSize", page.getTotal());
        return Result.ok(data);


    }

    @Override
    public Result updateUser(Customer customer) {
        customerMapper.updateById(customer);
        return Result.ok(null);
    }

    @Override
    public Result deleteUser(Integer id) {
        customerMapper.deleteById(id);
        return Result.ok(null);
    }


}

