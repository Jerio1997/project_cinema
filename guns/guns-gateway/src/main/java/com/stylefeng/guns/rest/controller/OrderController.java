package com.stylefeng.guns.rest.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.order.OrderService;
import com.stylefeng.guns.api.order.vo.OrderVO;
import com.stylefeng.guns.rest.config.properties.JwtProperties;
import com.stylefeng.guns.rest.modular.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("order")
public class OrderController {

    @Reference(interfaceClass = OrderService.class,check = false)
    private OrderService orderService;

    @Autowired
    private RedisTemplate redisTemplate;

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

    /*@RequestMapping(value = "getOrderInfo",method = RequestMethod.POST)
    public ResponseVO getOrderInfo(
            @RequestParam(name = "nowPage",required = false,defaultValue = "1")Integer nowPage ,
            @RequestParam(name = "pageSize",required = false,defaultValue = "5")Integer pageSize,HttpServletRequest request) {

        //获取用户Id
        String token = request.getHeader(jwtProperties.getHeader());
        Integer userId = (Integer) redisTemplate.opsForValue().get(token);

        Page<OrderVO> page = new Page<>(nowPage,pageSize);
        Page<OrderVO> result = orderService.getOrderByUserId(userId,page );
        ArrayList<Object> list = new ArrayList<>();
        List<OrderVO> readis = result.getRecords();
        int totalPage = (int)result.getPages();
        list.add(readis);
        return ResponseVO.success(nowPage,totalPage,"",list);
    }*/
    //获取订单信息
    @RequestMapping(value = "getOrderInfo",method = RequestMethod.POST)
    public ResponseVO getOrderInfo(
            @RequestParam(name = "nowPage",required = false,defaultValue = "1")Integer nowPage ,
            @RequestParam(name = "pageSize",required = false,defaultValue = "5")Integer pageSize) {

        //获取用户Id
        String token = request.getHeader(jwtProperties.getHeader());
        Integer userId = (Integer) redisTemplate.opsForValue().get(token);

        Page<OrderVO> page = new Page<>(nowPage,pageSize);

        if (userId != null){
            Page<OrderVO> total = orderService.getOrderByUserId(userId, page);

            return ResponseVO.success(nowPage, (int) total.getPages(),"",total.getRecords());
        }else {
            return ResponseVO.serviceFail("订单列表为空哦！~");
        }
    }
}
