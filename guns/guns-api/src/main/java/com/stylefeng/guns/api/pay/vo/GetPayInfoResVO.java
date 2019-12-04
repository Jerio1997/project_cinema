package com.stylefeng.guns.api.pay.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class GetPayInfoResVO implements Serializable {
    private static final long serialVersionUID = -5837357601141679760L;
    /**
     * orderId : 523
     * qRCodeAddress : /project4/server/resources/pic/qr-523.png
     */
    private Integer orderId;

    private String qRCodeAddress;

}
