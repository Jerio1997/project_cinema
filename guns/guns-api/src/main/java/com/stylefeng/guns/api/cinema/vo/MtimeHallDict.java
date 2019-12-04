package com.stylefeng.guns.api.cinema.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 地域信息表
 * </p>
 *
 * @author Jerio
 * @since 2019-11-28
 */
@Data
public class MtimeHallDict implements Serializable {


    private static final long serialVersionUID = 605434927616579178L;
    /**
     * 主键编号
     */
    private Integer uuid;
    /**
     * 显示名称
     */
    private String showName;
    /**
     * 座位文件存放地址
     */
    private String seatAddress;


}
