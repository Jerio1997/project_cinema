package com.stylefeng.guns.api.film;


import com.stylefeng.guns.api.film.vo.*;

import java.util.List;

/**
 * Author Jerio
 * CreateTime 2019/11/27 17:47
 **/
public interface FilmService {

    List<BannerVO> getBanners();

    FilmVO getHotFilms(boolean isLimit, int nums);

    FilmVO getSoonFilms(boolean isLimit, int nums);

    List<FilmInfo> getBoxRanking(Integer count);

    List<FilmInfo> getExpectRanking(Integer count);

    List<FilmInfo> getTop(Integer count);

    List<CatVO> getCats(String catId);

    List<SourceVO> getSource(String sourceId);

    List<YearVO> getYears(String yearId);
}
