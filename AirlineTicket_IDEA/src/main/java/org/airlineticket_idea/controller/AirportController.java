package org.airlineticket_idea.controller;

import org.airlineticket_idea.pojo.Airline;
import org.airlineticket_idea.pojo.Airport;
import org.airlineticket_idea.pojo.vo.PageKeywords;
import org.airlineticket_idea.service.AirlineService;
import org.airlineticket_idea.service.AirportService;
import org.airlineticket_idea.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/airport")
@CrossOrigin
public class AirportController {

    @Autowired
    private AirportService airportService;
    @Autowired
    private AirlineService airlineService;

    //机场注册
    @PostMapping("/register")
    public Result register(@RequestBody Airport airport ){
        Result result =airportService.register(airport);
        return result;
    }
//机场登录
    @PostMapping("/login")
    public Result  login(@RequestBody Airport airport ){
        Result result = airportService.login(airport);
        return result;
    }
//机场查看航班
    @PostMapping("/airlines")
    public Result airlines(@RequestBody PageKeywords pageKeywords){
        Result result = airlineService.getAirlines(pageKeywords);
        return result;
    }

//机场添加航班
    @PostMapping("/addAirline")
    public Result addAirline(@RequestBody Airline airline){
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
    public Result deleteAirline(@RequestParam("id")Integer id) {
        Result result = airlineService.deleteAirline(id);
        return result;
    }
    //todo 下面是正在进行的接口
    //机场查询所有同一机场的管理员信息接口
    @GetMapping("/admin")
    public Result admin(){
        Result result = airportService.admin();
        return result;
    }
    //机场修改管理员信息接口
    @PutMapping("/updateAdmin")
    public Result updateAdmin(@RequestBody Airport airport){
        Result result = airportService.updateAdmin(airport);
        return result;
    }
    //机场删除管理员信息接口
    @DeleteMapping("/deleteAdmin")
    public Result deleteAdmin(@RequestParam("id")Integer id){
        Result result = airportService.deleteAdmin(id);
        return result;
    }

    //机场查询所有用户的接口，可以根据id、name、phone来查询用户信息
    @GetMapping("/users")
    public Result users(){
        Result result = airportService.users();
        return result;
    }
    //机场修改用户信息接口
    @PutMapping("/updateUser")
    public Result updateUser(@RequestBody Airport airport){
        Result result = airportService.updateUser(airport);
        return result;
    }
    //机场删除用户信息接口
    @DeleteMapping("/deleteUser")
    public Result deleteUser(@RequestParam("id")Integer id){
        Result result = airportService.deleteUser(id);
        return result;
    }
    //机场查询飞机接口
    @GetMapping("/planes")
    public Result planes(){
        Result result = airportService.planes();
        return result;
    }
    //机场添加飞机接口
    @PostMapping("/addPlane")
    public Result addPlane(@RequestBody Airport airport){
        Result result = airportService.addPlane(airport);
        return result;
    }
    //机场修改飞机接口
    @PutMapping("/updatePlane")
    public Result updatePlane(@RequestBody Airport airport){
        Result result = airportService.updatePlane(airport);
        return result;
    }
    //机场删除飞机接口
    @DeleteMapping("/deletePlane")
    public Result deletePlane(@RequestParam("id")Integer id){
        Result result = airportService.deletePlane(id);
        return result;
}
