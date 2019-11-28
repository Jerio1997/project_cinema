package com.stylefeng.guns.api.film.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: guns-parent
 * @description: 封装查到的banner信息
 * @author: Helios1102
 * @create: 2019-11-28 20:18
 */

@Data
public class BannerVO implements Serializable {

    private String bannerId;

    private String bannerAddress;

    private String bannerUrl;
}
