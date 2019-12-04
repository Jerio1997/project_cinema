package com.stylefeng.guns.order.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.api.order.OrderService;
import com.stylefeng.guns.api.order.vo.MtimeOrder;
import com.stylefeng.guns.api.order.vo.OrderVO;
import com.stylefeng.guns.order.common.persistence.dao.*;
import com.stylefeng.guns.order.common.persistence.model.*;
import com.stylefeng.guns.order.util.ConnectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import com.stylefeng.guns.order.common.persistence.dao.MoocOrderTMapper;
import com.stylefeng.guns.order.common.persistence.model.MoocOrderT;
import com.stylefeng.guns.order.common.persistence.dao.MtimeFieldTMapper;
import com.stylefeng.guns.order.common.persistence.dao.MtimeHallDictTMapper;
import com.stylefeng.guns.order.common.persistence.model.MtimeFieldT;
import com.stylefeng.guns.order.common.persistence.model.MtimeHallDictT;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
@Slf4j
@Service(interfaceClass = OrderService.class)
@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private MoocOrderTMapper orderMapper;

    @Autowired
    private MtimeFieldTMapper fieldTMapper;

    @Autowired
    private MtimeHallDictTMapper hallDictTMapper;

    @Autowired
    private MtimeCinemaTMapper cinemaTMapper;

    @Autowired
    private MtimeFilmTMapper filmTMapper;


    /**
     * Jerio:判断座位状态
     * @param filedId 场次编号
     * @param seatId 座位编号 若是多个用','隔开
     * @return false是该座位不可用 true是该座位可用
     */
    @Override
    public Boolean isTrueSeats(String filedId, String seatId) {
        /*Map<String,Object> map = new HashMap<>();
        map.put("uuid",filedId);
        List<MtimeFieldT> fields = fieldTMapper.selectByMap(map);
        if(fields.size() != 1){
            //表示根据这个场次编号在field没有找到该条信息
            return false;
        }*/
        MtimeFieldT mtimeFieldT = fieldTMapper.selectById(filedId);
        Integer hallId = mtimeFieldT.getHallId();
        Map<String,Object> map2 = new HashMap<>();
        map2.put("uuid",hallId);
        List<MtimeHallDictT> halls = hallDictTMapper.selectByMap(map2);
        if(halls.size() != 1){
            //表示根据这个hallId在hall表没能找到信息
            return false;
        }
        String seatAddress = halls.get(0).getSeatAddress();
        /*下面通过seatAddress得到json文件*/
        String uri = "http://localhost:1818/" + seatAddress;
        String input = ConnectionUtils.readFileToString(uri);
        JSONObject jsonObject = JSONObject.parseObject(input);
        String idsString = jsonObject.getString("ids");
        String[] ids = idsString.split(",");
        /*得到ids后，判断if contains 每一个seatId*/
        String[] seats = splitSeatIds(seatId);
        for (String seat : seats) {
            if(Integer.parseInt(seat) > ids.length){
                return false;
            }
        }
        return true;
    }

    private String[] splitSeatIds(String seatId) {
        if(!seatId.contains(",")){
            //表示该seatId仅一个id
            String[] seat = new String[]{seatId};
            return seat;
        }
        String[] seats = seatId.split(",");
        return seats;
    }

    @Override
    public Boolean isSoldSeats(String filedId, String seatId) {
        Map<String,Object> map = new HashMap<>();
        map.put("field_id",filedId);
        List<MoocOrderT> orders = orderMapper.selectByMap(map);
        if(orders.size() == 0){
            //表示还没有该场次的订单，所以该座位没被卖掉
            return false;
        }
        String[] inSeatsId = splitSeatIds(seatId);
        for (MoocOrderT order : orders) {
            String seatsIds = order.getSeatsIds();
            String[] seats = seatsIds.split(",");
            for (String seat : seats) {
                for (String s : inSeatsId) {
                    if(seat.equals(s)) {
                        //表示有相同的座位已经卖掉了
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 生成订单信息
     * @param fieldId 场次编号
     * @param soldSeats 座位编号 可能是多个 用','隔开
     * @param seatsName 座位名称
     * @param userId 用户ID
     * @return
     */
    @Override
    public OrderVO saveOrderInfo(String fieldId, String soldSeats, String seatsName, Integer userId) {
        OrderVO orderVO = new OrderVO();//要返回给报文的
        MoocOrderT order = new MoocOrderT();//要插入sql的
        //orderId怎么生成/获得？
        MtimeFieldT field = fieldTMapper.selectById(fieldId);
        MtimeCinemaT cinema = cinemaTMapper.selectById(field.getCinemaId());
        orderVO.setCinemaName(cinema.getCinemaName());
        orderVO.setFieldTime(field.getBeginTime());
        MtimeFilmT film = filmTMapper.selectById(field.getFilmId());
        orderVO.setFilmName(film.getFilmName());
        String[] seats = splitSeatIds(soldSeats);
        int number = seats.length;
        int totalPrice = number*field.getPrice();
        orderVO.setOrderPrice(new BigDecimal(totalPrice));
        orderVO.setOrderTimestamp((new Date()).getTime());
        //还差个获得座位名称------后记:发现想太多了，seatName已经给了
        /*MtimeHallDictT hall = hallDictTMapper.selectById(field.getHallId());
        String address = hall.getSeatAddress();
        String uri = "http://localhost:1818/" + address;
        String input = ConnectionUtils.readFileToString(uri);
        JSONObject jsonObject = JSONObject.parseObject(input);
        String single = jsonObject.getString("single");*/
        orderVO.setSeatsName(seatsName);
        //以上就是orderVO部分

        //以下是order部分
        order.setCinemaId(cinema.getUuid());
        order.setFieldId(field.getUuid());
        order.setFilmId(film.getUuid());
        //----这个已售座位编号与名称怎么整？---它直接给了------
        order.setSeatsIds(soldSeats);
        order.setSeatsName(seatsName);
        //------已售座位编号与名称---------------

        order.setFilmPrice((double)(field.getPrice()));
        order.setOrderPrice(new BigDecimal(totalPrice));
        order.setOrderTime(new Date());
        order.setOrderUser(userId);
        order.setOrderStatus(0);
        orderMapper.insert(order);
//        int lastId = orderMapper.getLastId();
        orderVO.setOrderId(order.getUuid());
        return orderVO;
    }

   /* @Override
    public Page<OrderVO> getOrderByUserId(Integer userId, Page<OrderVO> page) {
        Page<OrderVO> orderVo = new Page<>();
        if (userId == null){
            return null;
        }else {

            Map<String,Object> map = new HashMap<>();
//            map.put("")
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
    }*/

    @Override
    public String getSoldSeatsByFieldId(Integer fieldId) {
        if(fieldId == null){
            log.error("查询已售座位错误，没有传入场次编号");
            return null;
        }else{
//            Map<String,Object> map = new HashMap<>();
//            map.put("field_id",fieldId);
//            map.put("order_status",1);
//            List<MoocOrderT> orderList = orderMapper.selectByMap(map);
            EntityWrapper<MoocOrderT> moocOrderTEntityWrapper = new EntityWrapper<>();
            moocOrderTEntityWrapper.eq("field_id",fieldId).and().in("order_status","0,1");
            List<MoocOrderT> orderList = orderMapper.selectList(moocOrderTEntityWrapper);
            StringBuffer result = new StringBuffer();
            if(orderList.size() != 0){
                for (MoocOrderT order : orderList) {
                    result.append(",");
                    result.append(order.getSeatsIds());
                }
                return result.substring(1);
            }else{
                return null;
            }

        }
    }

    @Override
    public List<OrderVO> getOrderByUserId(Integer userId) {
        List<OrderVO> orderList = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put("order_user",userId);
        List<MoocOrderT> orderTs= orderMapper.selectByMap(map);
        for (MoocOrderT orderT : orderTs) {
            OrderVO order = new OrderVO();
            MtimeCinemaT cinema = cinemaTMapper.selectById(orderT.getCinemaId());
            order.setCinemaName(cinema.getCinemaName());
            MtimeFieldT field = fieldTMapper.selectById(orderT.getFieldId());
            order.setFieldTime(field.getBeginTime());
            MtimeFilmT film = filmTMapper.selectById(orderT.getFilmId());
            order.setFilmName(film.getFilmName());
            order.setOrderId(orderT.getUuid());
            order.setOrderPrice(orderT.getOrderPrice());
            order.setOrderStatus(orderT.getOrderStatus());
            order.setOrderTimestamp(orderT.getOrderTime().getTime());
            order.setSeatsName(orderT.getSeatsIds());
            orderList.add(order);
        }
        return orderList;
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

    @Override
    public MtimeOrder queryOrderById(Integer orderId) {
        MoocOrderT moocOrderT = orderMapper.selectById(orderId);
        MtimeOrder mtimeOrder = new MtimeOrder();
        BeanUtils.copyProperties(moocOrderT,mtimeOrder);
        return mtimeOrder;
    }

    @Override
    public int updateOrderStatus(Integer orderId,Integer status) {
        MoocOrderT moocOrderT = orderMapper.selectById(orderId);
        moocOrderT.setOrderStatus(status);
        Integer integer = orderMapper.updateById(moocOrderT);
        return integer;
    }
}
