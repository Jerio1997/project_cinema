package com.stylefeng.guns.api.pay.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseResVO<T> implements Serializable {

    private static final long serialVersionUID = -6162353579325694848L;
    /**
     * data : {"orderId":"523","qRCodeAddress":"/project4/server/resources/pic/qr-523.png"}
     * imgPre : http://www.duolaima.com
     * msg :
     * nowPage :
     * status : 0
     * totalPage :
     */

    private T data;
    private String imgPre;
    private String msg;
    private Integer nowPage;
    private Integer status;
    private Integer totalPage;

}
