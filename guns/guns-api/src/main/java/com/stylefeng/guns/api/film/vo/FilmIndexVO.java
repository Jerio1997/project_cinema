package com.stylefeng.guns.api.film.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用于封装 /film/getIndex 请求中响应的FilmResoVO中的data
 */
@Data
public class FilmIndexVO implements Serializable {

    private List<BannerVO> banners;

    private FilmVO hotFilms;

    private FilmVO soonFilms;

    private List<FilmInfo> boxRanking;

    private List<FilmInfo> exceptRanking;

    private List<FilmInfo> top100;

}
