package com.stylefeng.guns.order.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.api.order.OrderService;
import com.stylefeng.guns.api.order.vo.OrderVO;
import com.stylefeng.guns.order.common.persistence.dao.MoocOrderTMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Service
@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private MoocOrderTMapper orderMapper;

    @Override
    public Boolean isTrueSeats(String filedId, String seatId) {

        return null;
    }

    @Override
    public Boolean isSoldSeats(String filedId, String seatId) {
        return null;
    }

    @Override
    public OrderVO saveOrderInfo(String fieldId, String soldSeats, String seatsName, String userId) {
        return null;
    }
}
