package com.stylefeng.guns.order.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.order.OrderService;
import com.stylefeng.guns.api.order.vo.OrderVO;
import com.stylefeng.guns.order.common.persistence.dao.MoocOrderTMapper;
import com.stylefeng.guns.order.common.persistence.model.MoocOrderT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Slf4j
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

    @Override
    public Page<OrderVO> getOrderByUserId(Integer userId, Page<OrderVO> page) {
        Page<OrderVO> orderVo = new Page<>();
        if (userId == null){
            return null;
        }else {
            List<OrderVO> orderVOList = orderMapper.getOrdersByUserId(userId,page);

            if (orderVOList == null && orderVOList.size() == 0){
                orderVo.setTotal(0);
                orderVo.setRecords(new ArrayList<>());
                return orderVo;
            }else {
                EntityWrapper<MoocOrderT> entityWrapper = new EntityWrapper<>();
                entityWrapper.eq("order_user",userId);
                Integer all = orderMapper.selectCount(entityWrapper);

                orderVo.setTotal(all);
                orderVo.setRecords(orderVOList);

                return orderVo;
            }
        }
    }

    @Override
    public String getSoldSeatsByFieldId(Integer fieldId) {
        if(fieldId == null){
            log.error("查询已售座位错误，没有传入场次编号");
            return "";
        }else{
            String soldSeatsByFieldId = orderMapper.getSoldSeatsByFieldId(fieldId);
            return soldSeatsByFieldId;
        }
    }

    /*@Override
    public Page<OrderVO> getOrderByUserId(Integer userId, Page<OrderVO> page) {
        Page<OrderVO> page1 = new Page<>();
        if (userId == null){
            return null;
        }else {
            EntityWrapper wrapper = new EntityWrapper();
            wrapper.eq("order_user",userId);
            List<OrderVO> list = orderMapper.selectList(wrapper);
            if (CollectionUtils.isEmpty(list)){
                page1.setTotal(0);
                page1.setRecords(new ArrayList<>());
                return page1;
            }else {
                EntityWrapper wrapper1 = new EntityWrapper();
                wrapper1.eq("order",userId);
                Integer integer = orderMapper.selectCount(wrapper1);
                page1.setTotal(integer);
                page1.setRecords(list);
                return page1;
            }
        }
    }*/
}
