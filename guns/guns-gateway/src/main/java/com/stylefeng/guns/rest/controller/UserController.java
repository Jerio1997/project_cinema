package com.stylefeng.guns.rest.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.user.UserService;
import com.stylefeng.guns.rest.modular.vo.ResponseVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("user")
public class UserController {

    @Reference
    private UserService userService;

    /*@RequestMapping("register")
    public ResponseVO*/

    @GetMapping("logout")
    public ResponseVO logout(){
//        ResponseVO responseVO = new ResponseVO();
        return null;
    }


}
