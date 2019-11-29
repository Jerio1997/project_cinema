package com.stylefeng.guns.rest.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.film.FilmService;
import com.stylefeng.guns.api.film.vo.*;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "getFilms",method = RequestMethod.GET)
    public ResponseVo gitFilms(@RequestBody RequestFilmsVo requestFilmsVo){

        GetFilmVo getFilmVo = null;
        if (requestFilmsVo.getShowType() == 1){
            getFilmVo = filmService.getHotFilm(
                    false,requestFilmsVo.getPageSize(),requestFilmsVo.getNowPage(),
                    requestFilmsVo.getSortId(),requestFilmsVo.getSourceId(),
                    requestFilmsVo.getYearId(),requestFilmsVo.getCatId()
            );
        }
        if (requestFilmsVo.getShowType() == 2){
            getFilmVo = filmService.getSoonFilm(
                    false,requestFilmsVo.getPageSize(),requestFilmsVo.getNowPage(),
                    requestFilmsVo.getSortId(),requestFilmsVo.getSourceId(),
                    requestFilmsVo.getYearId(),requestFilmsVo.getCatId()
            );
        }
        if (requestFilmsVo.getShowType() == 3){
            getFilmVo = filmService.getClassicFilm(
                    false,requestFilmsVo.getPageSize(),requestFilmsVo.getNowPage(),
                    requestFilmsVo.getSortId(),requestFilmsVo.getSourceId(),
                    requestFilmsVo.getYearId(),requestFilmsVo.getCatId()
            );
        }
        else {
            getFilmVo = filmService.getHotFilm(
                    false,requestFilmsVo.getPageSize(),requestFilmsVo.getNowPage(),
                    requestFilmsVo.getSortId(),requestFilmsVo.getSourceId(),
                    requestFilmsVo.getYearId(),requestFilmsVo.getCatId()
            );
        }

        String img_pre = "http://img.meetingshop.cn/";

        return ResponseVo.success(img_pre,getFilmVo.getNowPage(),getFilmVo.getTotalPage(),getFilmVo.getGetFilmInfoList());
    }

    @RequestMapping(value = "films{searchFilm}",method = RequestMethod.GET)
    public ResponseVo films(@PathVariable("searchFilm")String searchFilm,
                            int searchType){
        //searchType : ‘0表示按照编号查找，1表示按照名称查找'

        FilmDetailVo filmDetailVo = filmService.getFilmDetail(searchType,searchFilm);

        if (filmDetailVo == null){
            return ResponseVo.serviceException("查询失败，无影片可加载");
        }else if (filmDetailVo.getFilmId()==null){
            return ResponseVo.serviceException("查询失败，无影片可加载");
        }
       String filmId = filmDetailVo.getFilmId();
        return null;
    }
}
