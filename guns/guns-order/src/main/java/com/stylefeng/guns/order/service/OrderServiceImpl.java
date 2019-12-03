package com.stylefeng.guns.order.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.order.OrderService;
import com.stylefeng.guns.api.order.vo.OrderVO;
import com.stylefeng.guns.order.common.persistence.dao.MoocOrderTMapper;
import com.stylefeng.guns.order.common.persistence.model.MoocOrderT;
import lombok.extern.slf4j.Slf4j;
import com.stylefeng.guns.order.common.persistence.dao.MtimeFieldTMapper;
import com.stylefeng.guns.order.common.persistence.dao.MtimeHallDictTMapper;
import com.stylefeng.guns.order.common.persistence.model.MtimeFieldT;
import com.stylefeng.guns.order.common.persistence.model.MtimeHallDictT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Slf4j
@Service
@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private MoocOrderTMapper orderMapper;

    @Autowired
    private MtimeFieldTMapper fieldTMapper;

    @Autowired
    private MtimeHallDictTMapper hallDictTMapper;


    /**
     * Jerio:判断座位状态
     * @param filedId 场次编号
     * @param seatId 座位编号
     * @return
     */
    @Override
    public Boolean isTrueSeats(String filedId, String seatId) {
        Map<String,Object> map = new HashMap<>();
        map.put("uuid",filedId);
        List<MtimeFieldT> fields = fieldTMapper.selectByMap(map);
        if(fields.size() != 1){
            //表示根据这个场次编号在field没有找到该条信息
            return false;
        }
        Integer hallId = fields.get(0).getHallId();
        Map<String,Object> map2 = new HashMap<>();
        map2.put("uuid",hallId);
        List<MtimeHallDictT> halls = hallDictTMapper.selectByMap(map2);
        if(halls.size() != 1){
            //表示根据这个hallId在hall表没能找到信息
            return false;
        }
        String seatAddress = halls.get(0).getSeatAddress();
        /*怎么通过seatAddress得到json文件*/

        return null;
    }

    @Override
    public Boolean isSoldSeats(String filedId, String seatId) {
        return null;
    }

    @Override
    public OrderVO saveOrderInfo(String fieldId, String soldSeats, String seatsName, Integer userId) {
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
