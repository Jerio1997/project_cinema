package com.stylefeng.guns.api.film.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: guns-parent
 * @description: 构建响应报文，sourceVO
 * @author: Helios1102
 * @create: 2019-11-29 15:34
 */
@Data
public class SourceVO implements Serializable {

    private String sourceId;

    private String sourceName;

    private boolean isActive;

}
