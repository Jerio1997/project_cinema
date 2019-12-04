package com.stylefeng.guns.rest.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.order.OrderService;
import com.stylefeng.guns.api.order.vo.OrderVO;
import com.stylefeng.guns.api.user.UserService;
import com.stylefeng.guns.api.user.vo.UserInfo;
import com.stylefeng.guns.rest.config.properties.JwtProperties;
import com.stylefeng.guns.rest.modular.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
        if (!isTrueSeats) {
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
            @RequestParam(name = "pageSize",required = false,defaultValue = "5")Integer pageSize
    ) {

        //获取用户Id
        String token = request.getHeader(jwtProperties.getHeader());
        Integer userId = (Integer) redisTemplate.opsForValue().get(token);

        List<OrderVO> orderList = orderService.getOrderByUserId(userId);


        if(orderList.size() == 0){
            return ResponseVO.serviceFail("订单列表为空哦！~");
        } else {
            //计算totalpage
            int totalpage = getTotalpage(pageSize,orderList.size());
            return ResponseVO.success(nowPage,totalpage,orderList);
        }

//        Page<OrderVO> page = new Page<>(nowPage,pageSize);

        /*if (userId != null){
            Page<OrderVO> total = orderService.getOrderByUserId(userId, page);

            return ResponseVO.success(nowPage, (int) total.getPages(),"",total.getRecords());
        }else {
            return ResponseVO.serviceFail("订单列表为空哦！~");
        }*/
    }

    private int getTotalpage(Integer pageSize, int size) {
        int a = size/pageSize;
        int b = size%pageSize;
        if(b == 0){
            //余数是0
            return a;
        } else {
            return ++a;
        }
    }
}
