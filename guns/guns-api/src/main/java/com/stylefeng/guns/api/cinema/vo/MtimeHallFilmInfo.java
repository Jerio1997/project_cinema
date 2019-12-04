package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 影厅电影信息表
 * </p>
 *
 * @author Jerio
 * @since 2019-11-28
 */
@Data
public class MtimeHallFilmInfo implements Serializable {


    private static final long serialVersionUID = -6394851674289237942L;
    /**
     * 主键编号
     */
    private Integer uuid;
    /**
     * 电影编号
     */
    private Integer filmId;
    /**
     * 电影名称
     */

    private String filmName;
    /**
     * 电影时长
     */

    private String filmLength;
    /**
     * 电影类型
     */

    private String filmCats;
    /**
     * 电影语言
     */

    private String filmLanguage;
    /**
     * 演员列表
     */
    private String actors;
    /**
     * 图片地址
     */
    private String imgAddress;

}
