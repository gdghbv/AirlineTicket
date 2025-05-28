package org.airlineticket_idea.controller;

import org.airlineticket_idea.pojo.*;
import org.airlineticket_idea.pojo.dto.OrderKeywords;
import org.airlineticket_idea.pojo.dto.AirlineKeywords;
import org.airlineticket_idea.pojo.dto.PlaneKeywords;
import org.airlineticket_idea.pojo.dto.UserKeywords;
import org.airlineticket_idea.pojo.vo.ShowAirportStatVO;
import org.airlineticket_idea.service.*;
import org.airlineticket_idea.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/airport")
@CrossOrigin
public class AirportUserController {

    @Autowired
    private AirlineService airlineService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private AirplaneService airplaneService;

    @Autowired
    private AirportUserService airportUserService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private AirportService airportService;



    //机场注册
    @PostMapping("/register")
    public Result register(@RequestBody AirportUser airportUser) {
        Result result = airportUserService.register(airportUser);
        return result;
    }

    //机场登录
    @PostMapping("/login")
    public Result login(@RequestBody AirportUser airportUser) {
        Result result = airportUserService.login(airportUser);
        return result;
    }

    //获取管理员个人信息
    @GetMapping("/getUserInfo")
    public Result getUserInfo(@RequestHeader String token) {
        Result result = airportUserService.getUserInfo(token);
        return result;
    }

    //机场查询所有同一机场的管理员信息接口
    @GetMapping("/airportUsers")
    public Result admin(@RequestHeader String token) {
        Result result = airportUserService.admin(token);
        return result;
    }

    //机场修改管理员信息接口
    @PutMapping("/updateAirportUser")
    public Result updateAdmin(@RequestBody AirportUser airportUser) {
        Result result = airportUserService.updateAdmin(airportUser);
        return result;
    }

    //机场删除管理员信息接口
    @DeleteMapping("/deleteAirportUser")
    public Result deleteAdmin(@RequestParam("id") Integer id) {
        Result result = airportUserService.deleteAdmin(id);
        return result;
    }

    //机场查看航班
    @PostMapping("/airlines")
    public Result airlines(@RequestBody AirlineKeywords airlineKeywords) {
        Result result = airlineService.getAirlines(airlineKeywords,true);
        return result;
    }

    //机场添加航班
    @PostMapping("/addAirline")
    public Result addAirline(@RequestBody Airline airline) {
        Result result = airlineService.addAirline(airline);
        return result;
    }

    //机场修改航班
    @PutMapping("/updateAirline")
    public Result updateAirline(@RequestBody Airline airline) {
        Result result = airlineService.updateAirline(airline);
        return result;
    }

    //机场删除航班
    @DeleteMapping("/deleteAirline")
    public Result deleteAirline(@RequestParam("id") Integer id) {
        Result result = airlineService.deleteAirline(id);
        return result;
    }


    //机场查询所有用户的接口，可以根据id、name、phone来查询用户信息
    @PostMapping("/users")
    public Result users(@RequestBody(required = false) UserKeywords userKeywords) {
        Result result = customerService.users(userKeywords);
        return result;
    }

    //机场修改用户信息接口
    @PutMapping("/updateUser")
    public Result updateUser(@RequestBody Customer customer) {
        Result result = customerService.updateUser(customer);
        return result;
    }

    //机场删除用户信息接口
    @DeleteMapping("/deleteUser")
    public Result deleteUser(@RequestParam("id") Integer id) {
        Result result = customerService.deleteUser(id);
        return result;
    }

    //机场查询飞机接口
    @PostMapping("/planes")
    public Result planes(@RequestBody PlaneKeywords planeKeywords, @RequestHeader String token) {
        Result result = airplaneService.planes(planeKeywords, token);
        return result;
    }

    //机场添加飞机接口
    @PostMapping("/addPlane")
    public Result addPlane(@RequestBody Airplane airplane, @RequestHeader String token) {

        Result result = airplaneService.addPlane(airplane, token);
        return result;
    }

    //机场修改飞机接口
    @PutMapping("/updatePlane")
    public Result updatePlane(@RequestBody Airplane airplane) {
        Result result = airplaneService.updatePlane(airplane);
        return result;
    }

    //机场删除飞机接口
    @DeleteMapping("/deletePlane")
    public Result deletePlane(@RequestParam("id") Integer id) {
        Result result = airplaneService.deletePlane(id);
        return result;
    }
//获取机场前5名最多订单的航班信息
    @GetMapping("/topFiveAirlines")
    public Result showAirlineStat(){
        Result result =orderService.showAirlineStat();
        return result;
    }
    //获取机场中所有飞机的情况
    @GetMapping("/airplanesStat")
    public Result showPlaneStat(@RequestHeader String token){
        Result result =airplaneService.showPlaneStat(token);
        return result;
    }
    //获取机场的用户数量和订单数量
    @GetMapping("/userAndOrderCount")
    public Result showAirportStat(){
        ShowAirportStatVO vo=new ShowAirportStatVO();
        vo.setCustomerCount(customerService.count());
        vo.setOrderCount(orderService.count());
        vo.setAirportCount(airportService.count());
        return Result.ok(vo);

    }
    @PostMapping("/orders")
    public Result getOrders(@RequestBody OrderKeywords orderKeywords, @RequestHeader String token){
        Result result=orderService.getOrders(orderKeywords,token);
        return result;
    }
    @PutMapping("/updateOrder")
    public Result updateOrder(@RequestBody Order order){
        Result result=orderService.updateOrder(order);
        return result;
    }
    }
