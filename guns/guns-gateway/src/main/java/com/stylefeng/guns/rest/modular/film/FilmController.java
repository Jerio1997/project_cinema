package com.stylefeng.guns.rest.modular.film;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.film.FilmService;
import com.stylefeng.guns.api.film.vo.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Author Jerio
 * CreateTime 2019/11/27 21:31
 **/
@RestController
@RequestMapping("film")
public class FilmController {

    @Reference(interfaceClass = FilmService.class,check = false)
    private FilmService filmService;

    @RequestMapping("getIndex")
    public FilmRespVO getIndex(){

        List<BannerVO> banners = filmService.getBanners();
        FilmVO hotFilms = filmService.getHotFilms(true, 8);
        FilmVO soonFilms = filmService.getSoonFilms(true, 8);
        List<FilmInfo> boxRanking = filmService.getBoxRanking(10);
        List<FilmInfo> expectRanking = filmService.getExpectRanking(10);
        List<FilmInfo> top = filmService.getTop(10);

        FilmIndexVO filmIndexVO = new FilmIndexVO();
        filmIndexVO.setBanners(banners);
        filmIndexVO.setHotFilms(hotFilms);
        filmIndexVO.setSoonFilms(soonFilms);
        filmIndexVO.setBoxRanking(boxRanking);
        filmIndexVO.setExceptRanking(expectRanking);
        filmIndexVO.setTop100(top);

        FilmRespVO filmRespVO = new FilmRespVO();
        filmRespVO.setData(filmIndexVO);

        return filmRespVO;
    }

}
