package com.stylefeng.guns.api.order.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 订单信息表
 * </p>
 *
 * @author Jerio
 * @since 2019-12-02
 */
@Data
public class MtimeOrder implements Serializable {


    private static final long serialVersionUID = 7050565229851645820L;

    private Integer uuid;

    private Integer cinemaId;

    private Integer fieldId;

    private Integer filmId;

    private String seatsIds;

    private String seatsName;

    private Double filmPrice;

    private BigDecimal orderPrice;

    private Date orderTime;

    private Integer orderUser;

    private Integer orderStatus;

}
