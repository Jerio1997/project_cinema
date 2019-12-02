package com.stylefeng.guns.rest.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.order.OrderService;
import com.stylefeng.guns.api.order.vo.OrderVO;
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

    @PostMapping("buyTickets")
    public ResponseVO buyTickets(String fieldId, String soldSeats, String seatsName) {
        // 获取下单人信息
        String token = request.getHeader(jwtProperties.getHeader());
        String userId = (String) redisTemplate.opsForValue().get(token);

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
