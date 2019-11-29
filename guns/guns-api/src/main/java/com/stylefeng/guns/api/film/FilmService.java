package com.stylefeng.guns.api.film;


import com.stylefeng.guns.api.film.vo.BannerVO;
import com.stylefeng.guns.api.film.vo.FilmInfo;
import com.stylefeng.guns.api.film.vo.FilmVO;

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
}
