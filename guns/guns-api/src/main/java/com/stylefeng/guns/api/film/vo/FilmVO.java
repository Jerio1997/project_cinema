package com.stylefeng.guns.api.film.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @program: guns-parent
 * @description:
 * @author: Helios1102
 * @create: 2019-11-28 20:18
 */

@Data
public class FilmVO implements Serializable {

    private Integer filmNum;

    private Integer nowPage;

    private Integer totalPage;

    private List<FilmInfo> filmInfo;
}
