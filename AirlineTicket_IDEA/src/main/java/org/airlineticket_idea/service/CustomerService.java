package org.airlineticket_idea.service;

import org.airlineticket_idea.pojo.Customer;
import com.baomidou.mybatisplus.extension.service.IService;
import org.airlineticket_idea.pojo.vo.PageKeywords;
import org.airlineticket_idea.utils.Result;

/**
* @author 35461
* @description 针对表【customer】的数据库操作Service
* @createDate 2025-05-05 01:45:29
*/
public interface CustomerService extends IService<Customer> {

    Result register(Customer customer);

    Result login(Customer customer);

    Result getCustomerAirline(PageKeywords pageKeywords);

    Result getUserInfo(String token);

    Result modifyUserInfo(String token, Customer customer);

    Result getHistoryOrder(String token, PageKeywords pageKeywords);
}
