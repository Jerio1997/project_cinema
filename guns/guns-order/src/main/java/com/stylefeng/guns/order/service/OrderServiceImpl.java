package com.stylefeng.guns.order.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.api.order.OrderService;
import com.stylefeng.guns.api.order.vo.OrderVO;
import com.stylefeng.guns.order.common.persistence.dao.MoocOrderTMapper;
import com.stylefeng.guns.order.common.persistence.dao.MtimeFieldTMapper;
import com.stylefeng.guns.order.common.persistence.dao.MtimeHallDictTMapper;
import com.stylefeng.guns.order.common.persistence.model.MoocOrderT;
import com.stylefeng.guns.order.common.persistence.model.MtimeFieldT;
import com.stylefeng.guns.order.common.persistence.model.MtimeHallDictT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * @param seatId 座位编号 若是多个用','隔开
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
        /*
        得到ids后，判断if contains seatId
         */
        String[] seat = splitSeatIds(seatId);
        return null;
    }

    private String[] splitSeatIds(String seatId) {
        return null;
    }

    @Override
    public Boolean isSoldSeats(String filedId, String seatId) {
        Map<String,Object> map = new HashMap<>();
        map.put("field_id",filedId);
        List<MoocOrderT> orders = orderMapper.selectByMap(map);
        if(orders.size() == 0){
            //表示还没有该场次的订单，所以可以直接买
            return true;
        }
        for (MoocOrderT order : orders) {
            String seatsIds = order.getSeatsIds();
            String[] seats = seatsIds.split(",");
            for (String seat : seats) {
                if(seat.equals(seatId)){
                    //表示相同的座位已经卖掉了
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 生成订单信息
     * @param fieldId 场次编号
     * @param soldSeats 座位编号
     * @param seatsName 座位名称
     * @param userId 用户ID
     * @return
     */
    @Override
    public OrderVO saveOrderInfo(String fieldId, String soldSeats, String seatsName, Integer userId) {
        OrderVO orderVO = new OrderVO();
        return null;
    }
}
