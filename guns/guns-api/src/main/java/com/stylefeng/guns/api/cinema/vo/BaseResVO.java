package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

@Data
public class BaseResVO<T> {
    
    private String imgPre;
    private String msg;
    private Integer nowPage;
    private Integer status;
    private Integer totalPage;
    private T data;

}
