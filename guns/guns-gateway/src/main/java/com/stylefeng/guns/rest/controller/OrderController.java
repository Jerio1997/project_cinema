package com.stylefeng.guns.rest.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.order.OrderService;
import com.stylefeng.guns.api.order.vo.OrderVO;
import com.stylefeng.guns.api.user.UserService;
import com.stylefeng.guns.api.user.vo.UserInfo;
import com.stylefeng.guns.rest.config.properties.JwtProperties;
import com.stylefeng.guns.rest.modular.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Reference(check = false)
    private OrderService orderService;

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private HttpServletRequest request;

    @Reference
    private UserService userService;

    @PostMapping("buyTickets")
    public ResponseVO buyTickets(String fieldId, String soldSeats, String seatsName) {
        // 获取下单人信息
        String requestHeader = request.getHeader(jwtProperties.getHeader());
        if (!(requestHeader != null && requestHeader.startsWith("Bearer "))){
            //表示这个请求就没带上token，也就是没有登陆的状态
            ResponseVO.serviceFail("登陆信息异常，请重新登陆");
        }

        String token = requestHeader.substring(7);
        Integer userId = (Integer) redisTemplate.opsForValue().get(token);
        UserInfo user = userService.getUserById(userId);

        // 判断座位的真实性
        Boolean isTrueSeats = orderService.isTrueSeats(fieldId, soldSeats);
        if (isTrueSeats) {
            return ResponseVO.serviceFail("该座位不存在");
        }

        // 判断座位是否已经被售卖
        Boolean isSoldSeats = orderService.isSoldSeats(fieldId, soldSeats);
        if (isSoldSeats) {
            return ResponseVO.serviceFail("该座位已被卖出");
        }

        // 插入一天订单并返回
        OrderVO orderVO = orderService.saveOrderInfo(fieldId, soldSeats, seatsName, userId);
        return ResponseVO.success(orderVO);
    }
}
