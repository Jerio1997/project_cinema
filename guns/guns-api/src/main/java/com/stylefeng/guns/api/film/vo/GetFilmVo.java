package com.stylefeng.guns.api.film.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class GetFilmVo implements Serializable {

    private Integer filmNum;

    private Integer nowPage;

    private Integer totalPage;

    private List<GetFilmInfo> getFilmInfoList;

}
