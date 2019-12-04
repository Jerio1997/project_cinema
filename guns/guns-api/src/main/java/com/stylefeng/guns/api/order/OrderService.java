package com.stylefeng.guns.api.order;



import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.order.vo.MtimeOrder;
import com.stylefeng.guns.api.order.vo.OrderVO;

import java.util.List;

public interface OrderService {

    Boolean isTrueSeats(String filedId, String seatId);

    Boolean isSoldSeats(String filedId,String seatId);

    OrderVO saveOrderInfo(String fieldId, String soldSeats, String seatsName, Integer userId);

    String getSoldSeatsByFieldId(Integer fieldId);

    List<OrderVO> getOrderByUserId(Integer userId);

    //List<OrderVO> getOrderByUserId(Integer userId, Integer nowPage, Integer pageSize);

    MtimeOrder queryOrderById(Integer orderId);

    int updateOrderStatus(Integer orderId,Integer status);
}
