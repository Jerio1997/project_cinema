package com.stylefeng.guns.rest.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.pay.PayService;
import com.stylefeng.guns.api.pay.vo.BaseResVO;
import com.stylefeng.guns.api.pay.vo.GetPayInfoResVO;
import com.stylefeng.guns.api.pay.vo.GetPayResultResVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")
public class PayController {

    @Reference(interfaceClass = PayService.class,check = false)
    PayService payService;

    @RequestMapping("getPayInfo")
    public BaseResVO<GetPayInfoResVO> getPayInfo(Integer orderId){
        BaseResVO<GetPayInfoResVO> baseResVO  = payService.getPayInfo(orderId);
        return baseResVO;
    }

    @RequestMapping("getPayResult")
    public BaseResVO<GetPayResultResVO> getPayResult(Integer orderId, Integer tryNums){
        BaseResVO<GetPayResultResVO> baseResVO = payService.getPayResult(orderId,tryNums);
        return baseResVO;
    }
}
