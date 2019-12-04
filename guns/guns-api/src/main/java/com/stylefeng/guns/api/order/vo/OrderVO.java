package com.stylefeng.guns.api.order.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class OrderVO implements Serializable {
    private Integer orderId;
    private String filmName;
    private String fieldTime;
    private String cinemaName;
    private String seatsName;
    private BigDecimal orderPrice;
    private Long orderTimestamp;
    private Integer orderStatus;
}
