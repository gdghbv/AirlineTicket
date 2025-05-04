package org.justworking.airlineticket_idea.service;

import org.justworking.airlineticket_idea.pojo.Customer;
import com.baomidou.mybatisplus.extension.service.IService;
import org.justworking.airlineticket_idea.utils.Result;

/**
* @author 35461
* @description 针对表【customer】的数据库操作Service
* @createDate 2025-05-04 02:40:34
*/
public interface CustomerService extends IService<Customer> {

    Result register(Customer customer);
}
