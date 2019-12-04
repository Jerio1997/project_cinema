package com.stylefeng.guns.api.pay.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class GetPayResultResVO implements Serializable {

    private static final long serialVersionUID = -6561398677329409900L;
    private Integer orderId;

    private String orderMsg;

    private Integer orderStatus;


}
