package com.stylefeng.guns.api.film.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 响应/film/getIndex请求
 */
@Data
public class FilmRespVO implements Serializable {

    private Integer status;

    //随便写一个，项目中没有用到
    private String imgPre;

    private Object data;

    private String msg;
}
