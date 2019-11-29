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

    GetFilmVo getHotFilm(boolean b, Integer pageSize, Integer nowPage, Integer sortId, Integer sourceId, Integer yearId, Integer catId);

    GetFilmVo getSoonFilm(boolean b, Integer pageSize, Integer nowPage, Integer sortId, Integer sourceId, Integer yearId, Integer catId);

    GetFilmVo getClassicFilm(boolean b, Integer pageSize, Integer nowPage, Integer sortId, Integer sourceId, Integer yearId, Integer catId);

    FilmDetailVo getFilmDetail(int searchType, String searchFilm);
}
