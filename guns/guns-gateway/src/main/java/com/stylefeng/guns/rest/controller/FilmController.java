package com.stylefeng.guns.rest.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.film.FilmService;
import com.stylefeng.guns.api.film.vo.*;
import org.springframework.util.CollectionUtils;
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
        FilmRespVO filmRespVO = new FilmRespVO();
        FilmIndexVO filmIndexVO = new FilmIndexVO();

        try {
            List<BannerVO> banners = filmService.getBanners();
            if(CollectionUtils.isEmpty(banners)){
                filmRespVO.setStatus(1);
                filmRespVO.setMsg("查询失败，无banner可加载");
                return filmRespVO;
            }
            FilmVO hotFilms = filmService.getHotFilms(true, 8);
            FilmVO soonFilms = filmService.getSoonFilms(true, 8);
            List<FilmInfo> boxRanking = filmService.getBoxRanking(10);
            List<FilmInfo> expectRanking = filmService.getExpectRanking(10);
            List<FilmInfo> top = filmService.getTop(10);

            filmIndexVO.setBanners(banners);
            filmIndexVO.setHotFilms(hotFilms);
            filmIndexVO.setSoonFilms(soonFilms);
            filmIndexVO.setBoxRanking(boxRanking);
            filmIndexVO.setExceptRanking(expectRanking);
            filmIndexVO.setTop100(top);

        } catch (Exception e) {
            filmRespVO.setStatus(999);
            filmRespVO.setMsg("系统出现异常，请联系管理员");
            return filmRespVO;
        }
        filmRespVO.setData(filmIndexVO);
        filmRespVO.setStatus(0);
        filmRespVO.setImgPre("http://img.meetingshop.cn/");

        return filmRespVO;
    }

}
