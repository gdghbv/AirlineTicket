package org.airlineticket_idea.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.airlineticket_idea.pojo.Customer;
import org.airlineticket_idea.pojo.vo.PageKeywords;
import org.airlineticket_idea.service.CustomerService;
import org.airlineticket_idea.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
@CrossOrigin
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    //注册
    @PostMapping("/register")
    public Result register(@RequestBody Customer customer){
        Result result = customerService.register(customer);
        return result;
    }
    //登录并获取token返回给前端
    @PostMapping("/login")
    public Result login(@RequestBody Customer customer){
        Result result = customerService.login(customer);
        return result;
    }
    //根据分页条件和关键词来获取客户的航班信息
    @PostMapping("/getAirline")
public Result getCustomerAirline(@RequestBody PageKeywords pageKeywords){
    Result result = customerService.getCustomerAirline(pageKeywords);
        return result;
    }
    //查询个人的客户信息
    @GetMapping("/getUserInfo")
    public Result getUserInfo(@RequestHeader String token){
        Result result = customerService.getUserInfo(token);
        return result;
    }
    //修改个人的客户信息(目前只支持修改用户名和邮箱)
    @PutMapping("/modifyUserInfo")
    public Result modifyUserInfo(@RequestHeader String token,@RequestBody Customer customer){
        Result result=customerService.modifyUserInfo(token,customer);
        return result;
    }
    //客户查询自己的历史订单
@PostMapping("/getHistoryOrder")
    public Result getHistoryOrder(@RequestHeader String token,@RequestBody PageKeywords pageKeywords){
        Result result=customerService.getHistoryOrder(token,pageKeywords);
        return result;
}
    //客户购买订单

    //客户对订单进行退款

    //客户删除自己的历史订单
}


