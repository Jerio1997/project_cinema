package com.stylefeng.guns.api.order;



import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.order.vo.OrderVO;

public interface OrderService {

    Boolean isTrueSeats(String filedId, String seatId);

    Boolean isSoldSeats(String filedId,String seatId);

    OrderVO saveOrderInfo(String fieldId, String soldSeats, String seatsName, Integer userId);

    String getSoldSeatsByFieldId(Integer fieldId);

    Page<OrderVO> getOrderByUserId(Integer userId, Page<OrderVO> page);

    //List<OrderVO> getOrderByUserId(Integer userId, Integer nowPage, Integer pageSize);
}
