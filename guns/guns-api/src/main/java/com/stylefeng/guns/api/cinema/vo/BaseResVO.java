package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseResVO<T> implements Serializable {

    private static final long serialVersionUID = -3849230708898380062L;
    private String imgPre;
    private String msg;
    private Integer nowPage;
    private Integer status;
    private Integer totalPage;
    private T data;

}
