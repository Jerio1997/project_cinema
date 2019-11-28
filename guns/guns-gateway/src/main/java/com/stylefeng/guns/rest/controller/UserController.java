package com.stylefeng.guns.rest.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.user.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {




    @Reference
    private UserService userService;

//    @RequestMapping("register")

    /*@GetMapping("logout")
    public String logout(){

    }*/


}
