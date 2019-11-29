package com.stylefeng.guns.rest.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.user.UserService;
import com.stylefeng.guns.api.user.vo.User;
import com.stylefeng.guns.api.user.vo.UserInfo;
import com.stylefeng.guns.rest.modular.vo.ResponseVO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {

    @Reference(interfaceClass = UserService.class, check = false)
    private UserService userService;

    @PostMapping("register")
    public ResponseVO register(@RequestBody User user) {
        if (user.getUsername() == null || "".equals(user.getUsername().trim())) {
            return ResponseVO.serviceFail("请输入用户名");
        }
        if (user.getPassword() == null || "".equals(user.getPassword().trim())) {
            return ResponseVO.serviceFail("请输入密码");
        }

        boolean isRegister = userService.register(user);
        if (isRegister) {
            return ResponseVO.success("注册成功");
        } else {
            return ResponseVO.serviceFail("用户已存在");
        }

    }

    @PostMapping("check")
    public ResponseVO check(String username) {
        if (username == null || "".equals(username.trim())) {
            return ResponseVO.serviceFail("请输入用户名");
        }
        boolean isExist = userService.checkUsername(username);
        if (isExist) {
            return ResponseVO.serviceFail("用户已存在");
        } else {
            return ResponseVO.success("验证成功");
        }
    }

    @PostMapping("updateUserInfo")
    public ResponseVO updateUserInfo(@RequestBody UserInfo userInfo) {
        boolean isUpdate = userService.updateUserInfo(userInfo);
        if (isUpdate) {
            UserInfo user = userService.queryUserById(userInfo.getUuid());
            return ResponseVO.success(user);
        } else {
            return ResponseVO.serviceFail("用户信息修改失败");
        }
    }

    /*@GetMapping("logout")
    public ResponseVO logout(){

    }*/


    /*@GetMapping("getUserInfo")
    public*/


}
