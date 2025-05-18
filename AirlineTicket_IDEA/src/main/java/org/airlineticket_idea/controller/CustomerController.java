package org.airlineticket_idea.controller;

import org.airlineticket_idea.pojo.Customer;
import org.airlineticket_idea.pojo.Order;
import org.airlineticket_idea.pojo.dto.PageKeywords;
import org.airlineticket_idea.service.AirlineService;
import org.airlineticket_idea.service.CustomerService;
import org.airlineticket_idea.service.DeepSeekService;
import org.airlineticket_idea.service.OrderService;
import org.airlineticket_idea.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/customer")
@CrossOrigin
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    OrderService orderService;
     @Autowired
     AirlineService airlineService;
    @Autowired
    private DeepSeekService deepSeekService;
//咨询ai
    @PostMapping("/ask")
    public Mono<Result<String>> askAI(@RequestBody String question) {

        System.out.println("接受到了AI请求，正在返回");
        System.out.println(question);
        return deepSeekService.getAIResponse(question);
    }
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
public Result getAirlines(@RequestBody PageKeywords pageKeywords){
    Result result = airlineService.getAirlines(pageKeywords);
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
        Result result=orderService.getHistoryOrder(token,pageKeywords);
        return result;
}
    //客户购买订单
@PostMapping("/buyTicket")
    public Result buyTicket(@RequestHeader String token,@RequestBody Order order,boolean useDiscount){
        Result result=orderService.buyTicket(token,order,useDiscount);
        return result;
}
    //客户对订单进行退款
@PutMapping("/refundTicket")
    public Result refundTicket(@RequestHeader String token,@RequestBody Order order){
        Result result = orderService.refundTicket(token,order);
        return result;
}
//客户进行用户的注册，添加积分系统(即是把积分从-1修改为0则可以开始正常积分的获取)
    @PutMapping("/memberRegister")
    public Result memberRegister(@RequestHeader String token){
        Result result  =orderService.memberRegister(token);
        return result;
    }
}


