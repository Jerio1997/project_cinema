package com.stylefeng.guns.api.film.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @program: guns-parent
 * @description: 构建film模块第二个接口的响应报文
 * @author: Helios1102
 * @create: 2019-11-29 16:37
 */
@Data
public class FilmConditionVO implements Serializable {

    private List<CatVO> catInfo;

    private List<SourceVO> sourceInfo;

    private List<YearVO> yearInfo;

}
