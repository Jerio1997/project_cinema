package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 放映场次表
 * </p>
 *
 * @author Jerio
 * @since 2019-12-02
 */
@Data
public class MtimeField implements Serializable {


    private static final long serialVersionUID = 5478201011892404086L;
    /**
     * 主键编号
     */

    private Integer uuid;
    /**
     * 影院编号
     */

    private Integer cinemaId;
    /**
     * 电影编号
     */

    private Integer filmId;
    /**
     * 开始时间
     */

    private String beginTime;
    /**
     * 结束时间
     */

    private String endTime;
    /**
     * 放映厅类型编号
     */

    private Integer hallId;
    /**
     * 放映厅名称
     */

    private String hallName;
    /**
     * 票价
     */
    private Integer price;

}
