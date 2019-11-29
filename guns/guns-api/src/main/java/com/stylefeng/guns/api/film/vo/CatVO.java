package com.stylefeng.guns.api.film.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: guns-parent
 * @description: 构建响应报文 catvo
 * @author: Helios1102
 * @create: 2019-11-29 15:32
 */
@Data
public class CatVO implements Serializable {

    private String catId;

    private String catName;

    private boolean isActive;

}
