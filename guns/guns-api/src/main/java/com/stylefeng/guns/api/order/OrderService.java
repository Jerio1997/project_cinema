package com.stylefeng.guns.api.order;

import com.stylefeng.guns.api.order.vo.OrderVO;

public interface OrderService {

    Boolean isTrueSeats(String filedId, String seatId);

    Boolean isSoldSeats(String filedId,String seatId);

    OrderVO saveOrderInfo(String fieldId, String soldSeats, String seatsName, String userId);
}
