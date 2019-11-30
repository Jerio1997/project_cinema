/*
package com.stylefeng.guns.rest.modular.film;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.film.GetFilmsAndFilmsService;
import com.stylefeng.guns.api.film.vo.FilmDetailVo;
import com.stylefeng.guns.api.film.vo.GetFilmVo;
import com.stylefeng.guns.api.film.vo.RequestFilmsVo;
import com.stylefeng.guns.api.film.vo.ResponseVo;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("film")
public class GetFilmsAndFilmsController {

    @Reference(interfaceClass = GetFilmsAndFilmsService.class,check = false)
    private GetFilmsAndFilmsService getFilmsAndFilmsService;

    @RequestMapping(value = "getFilms",method = RequestMethod.GET)
    public ResponseVo gitFilms(@RequestBody RequestFilmsVo requestFilmsVo){

        GetFilmVo getFilmVo = null;
        if (requestFilmsVo.getShowType() == 1){
            getFilmVo = getFilmsAndFilmsService.getHotFilm(
                    false,requestFilmsVo.getPageSize(),requestFilmsVo.getNowPage(),
                    requestFilmsVo.getSortId(),requestFilmsVo.getSourceId(),
                    requestFilmsVo.getYearId(),requestFilmsVo.getCatId()
            );
        }
        if (requestFilmsVo.getShowType() == 2){
            getFilmVo = getFilmsAndFilmsService.getSoonFilm(
                    false,requestFilmsVo.getPageSize(),requestFilmsVo.getNowPage(),
                    requestFilmsVo.getSortId(),requestFilmsVo.getSourceId(),
                    requestFilmsVo.getYearId(),requestFilmsVo.getCatId()
            );
        }
        if (requestFilmsVo.getShowType() == 3){
            getFilmVo = getFilmsAndFilmsService.getClassicFilm(
                    false,requestFilmsVo.getPageSize(),requestFilmsVo.getNowPage(),
                    requestFilmsVo.getSortId(),requestFilmsVo.getSourceId(),
                    requestFilmsVo.getYearId(),requestFilmsVo.getCatId()
            );
        }
        else {
            getFilmVo = getFilmsAndFilmsService.getHotFilm(
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

        FilmDetailVo filmDetailVo =
        return null;
    }
}
*/

