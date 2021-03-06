package com.stylefeng.guns.rest.modular.auth.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.user.UserService;
import com.stylefeng.guns.api.user.vo.User;
import com.stylefeng.guns.api.user.vo.UserInfo;
import com.stylefeng.guns.api.user.vo.UserLoginResVO;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.util.MD5Util;
import com.stylefeng.guns.rest.common.exception.BizExceptionEnum;
import com.stylefeng.guns.rest.modular.auth.controller.dto.AuthRequest;
import com.stylefeng.guns.rest.modular.auth.controller.dto.AuthResponse;
import com.stylefeng.guns.rest.modular.auth.util.JwtTokenUtil;
import com.stylefeng.guns.rest.modular.auth.validator.IReqValidator;
import com.stylefeng.guns.rest.modular.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 请求验证的
 *
 * @author fengshuonan
 * @Date 2017/8/24 14:22
 */
@RestController
public class AuthController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Resource(name = "simpleValidator")
    private IReqValidator reqValidator;

    @Autowired
    private RedisTemplate redisTemplate;

    @Reference(interfaceClass = UserService.class, check = false)
    private UserService userService;


//    @RequestMapping(value = "auth")
    @RequestMapping(value = "${jwt.auth-path}")
    public ResponseVO createAuthenticationToken(AuthRequest authRequest) {
        UserLoginResVO userLoginResVO = new UserLoginResVO();
        //把用户信息返回过来
        /*String username = authRequest.getUserName();
        UserInfo user = userService.getUserByUsername(username);
        if(user == null){
            return ResponseVO.serviceFail("用户名或密码错误");
        }
        //校验用户名和密码
        boolean validate = judge(user,authRequest);*/
        String username = authRequest.getUserName();
        String password = authRequest.getPassword();
        int uuid = userService.judgeAndGetUUid(username,password);

        if (uuid != -1) {
            final String randomKey = jwtTokenUtil.getRandomKey();
            final String token = jwtTokenUtil.generateToken(authRequest.getUserName(), randomKey);
            redisTemplate.opsForValue().set(token,uuid);
            redisTemplate.expire(token,300, TimeUnit.SECONDS);
            userLoginResVO.setRandomKey(randomKey);
            userLoginResVO.setToken(token);
            return ResponseVO.success(userLoginResVO);
        } else {
            return ResponseVO.serviceFail("用户名或密码错误");
        }
    }


}
