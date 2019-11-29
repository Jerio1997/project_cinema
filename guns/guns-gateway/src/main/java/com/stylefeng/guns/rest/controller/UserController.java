package com.stylefeng.guns.rest.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.user.UserService;
import com.stylefeng.guns.api.user.vo.User;
import com.stylefeng.guns.rest.modular.vo.ResponseVO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {

    @Reference
    private UserService userService;

    @PostMapping("register")
    public ResponseVO register(@RequestBody User user) {

        User registerUser = userService.selectUserByUsernamePassword(user);
        if (registerUser == null) {
            boolean isRegister = userService.insertUserByUsernamePassword(user);
            if (isRegister) {
                return ResponseVO.success("注册成功");
            }
            return ResponseVO.appFail("系统出现异常，请联系管理员");
        }
        return ResponseVO.serviceFail("用户已存在");
    }

    /*@GetMapping("logout")
    public ResponseVO logout(){

    }*/


    /*@GetMapping("getUserInfo")
    public*/


}
