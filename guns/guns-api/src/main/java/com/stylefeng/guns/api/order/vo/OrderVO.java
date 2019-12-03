package com.stylefeng.guns.api.order.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderVO implements Serializable {
    private Integer orderId;
    private String filmName;
    private String fieldTime;
    private String cinemaNme;
    private String seatsName;
    private Integer orderPrice;
    private Long orderTimestamp;
}
