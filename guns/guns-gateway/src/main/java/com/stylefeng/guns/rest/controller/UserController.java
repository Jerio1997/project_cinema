package com.stylefeng.guns.rest.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.user.UserService;
import com.stylefeng.guns.api.user.vo.User;
import com.stylefeng.guns.api.user.vo.UserInfo;
import com.stylefeng.guns.rest.config.properties.JwtProperties;
import com.stylefeng.guns.rest.modular.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("user")
public class UserController {

    @Reference(interfaceClass = UserService.class, check = false)
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private JwtProperties jwtProperties;

    @PostMapping("register")
    public ResponseVO register(User user) {
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

    @GetMapping("logout")
    public ResponseVO logout(HttpServletRequest request){

        String requestHeader= request.getHeader(jwtProperties.getHeader());
        if (requestHeader != null && requestHeader.startsWith("Bearer ")){
            String token = requestHeader.substring(7);
            redisTemplate.delete(token);
            return ResponseVO.success("用户退出成功");
        }
        //表示没登陆
        return ResponseVO.serviceFail("退出失败，用户尚未登陆");
    }


    @GetMapping("getUserInfo")
    public ResponseVO getUserInfo(HttpServletRequest request){
        String requestHeader = request.getHeader(jwtProperties.getHeader());
        if (requestHeader != null && requestHeader.startsWith("Bearer ")){
            String token = requestHeader.substring(7);
            UserInfo user = (UserInfo) redisTemplate.opsForValue().get(token);
            return ResponseVO.success(user);
        }
        return ResponseVO.serviceFail("退出失败，用户尚未登陆");
    }


}
