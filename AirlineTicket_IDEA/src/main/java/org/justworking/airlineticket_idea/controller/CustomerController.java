package org.justworking.airlineticket_idea.controller;

import org.justworking.airlineticket_idea.pojo.Customer;
import org.justworking.airlineticket_idea.service.CustomerService;
import org.justworking.airlineticket_idea.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
@CrossOrigin
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @PostMapping("/register")
    public Result register(@RequestBody Customer customer){
        Result result = customerService.register(customer);
        return result;
    }
}
