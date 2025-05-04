package org.airlineticket_idea.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.airlineticket_idea.pojo.Customer;
import org.airlineticket_idea.service.CustomerService;
import org.airlineticket_idea.mapper.CustomerMapper;
import org.airlineticket_idea.utils.MD5Util;
import org.airlineticket_idea.utils.Result;
import org.airlineticket_idea.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author 35461
* @description 针对表【customer】的数据库操作Service实现
* @createDate 2025-05-05 01:45:29
*/
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer>
        implements CustomerService{
    @Autowired
    CustomerMapper customerMapper;
    @Override
    public Result register(Customer customer) {
        LambdaQueryWrapper<Customer> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Customer::getPhone, customer.getPhone());
        long count = customerMapper.selectCount(queryWrapper);
        if(count>0){
            return Result.build(null, ResultCodeEnum.USERNAME_USED);
        }
        customer.setPassword(MD5Util.encrypt(customer.getPassword()));
        customerMapper.insert(customer);
        return Result.ok(null);
    }
}
