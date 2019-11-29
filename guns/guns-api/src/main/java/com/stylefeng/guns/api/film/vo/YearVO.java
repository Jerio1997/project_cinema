package com.stylefeng.guns.api.film.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: guns-parent
 * @description: 构建响应报文，yearVO
 * @author: Helios1102
 * @create: 2019-11-29 15:36
 */
@Data
public class YearVO implements Serializable {

    private String yearId;

    private String yearName;

    private boolean isActive;

}
