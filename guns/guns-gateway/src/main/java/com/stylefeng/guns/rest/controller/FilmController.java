package com.stylefeng.guns.rest.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.RpcContext;
import com.stylefeng.guns.api.film.FilmService;
import com.stylefeng.guns.api.film.vo.*;
import com.stylefeng.guns.rest.modular.vo.ResponseVO;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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
            //查询数据库
            //参数是每页显示最大条目数，在service层使用
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

    @RequestMapping("getConditionList")
    public FilmRespVO getConditionList(String catId, String sourceId, String yearId) {

        //默认值是99
        if (catId == null) catId = "99";
        if (sourceId == null) sourceId = "99";
        if (yearId == null) yearId = "99";

        FilmRespVO filmRespVO = new FilmRespVO();
        FilmConditionVO filmConditionVO = new FilmConditionVO();

        try {
            //数据库中查得
            List<CatVO> catInfo = filmService.getCats(catId);
            List<SourceVO> sourceInfo = filmService.getSource(sourceId);
            List<YearVO> yearInfo = filmService.getYears(yearId);
            if (catInfo == null || sourceInfo == null || yearInfo == null) {
                filmRespVO.setStatus(1);
                filmRespVO.setMsg("查询失败，无条件可加载");
                return filmRespVO;
            }

            filmConditionVO.setCatInfo(catInfo);
            filmConditionVO.setSourceInfo(sourceInfo);
            filmConditionVO.setYearInfo(yearInfo);
        } catch (Exception e) {
            filmRespVO.setStatus(999);
            filmRespVO.setMsg("系统出现异常，请联系管理员");
            return filmRespVO;
        }

        filmRespVO.setData(filmConditionVO);
        filmRespVO.setStatus(0);
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
                            int searchType) throws ExecutionException, InterruptedException {
        //searchType : ‘0表示按照编号查找，1表示按照名称查找'

        FilmDetailVo filmDetailVo = filmService.getFilmDetail(searchType,searchFilm);

        if (filmDetailVo == null){
            return ResponseVo.serviceException("查询失败，无影片可加载");
        }else if (filmDetailVo.getFilmId()==null){
            return ResponseVo.serviceException("查询失败，无影片可加载");
        }
       String filmId = filmDetailVo.getFilmId();
        filmService.getFilmDesc(filmId);
        Future<FilmDescVO> filmDescVOFuture = RpcContext.getContext().getFuture();
        //图片信息
        filmService.getImgs(filmId);
        Future<ImgVO> imgVOFuture = RpcContext.getContext().getFuture();
        //导演信息
        filmService.getDectInfo(filmId);
        Future<ActorVO> actorVOFuture = RpcContext.getContext().getFuture();
        //演员信息
        filmService.getActors(filmId);
        Future<List<ActorVO>> actors = RpcContext.getContext().getFuture();

        InfoRequstVO infoRequstVO = new InfoRequstVO();

        ActorRequestVO actorRequestVO = new ActorRequestVO();
        actorRequestVO.setActors(actors.get());
        actorRequestVO.setDirector(actorVOFuture.get());

        infoRequstVO.setActors(actorRequestVO);
        infoRequstVO.setBiography(filmDescVOFuture.get().getBiography());
        infoRequstVO.setFilmId(filmId);
        infoRequstVO.setImgVO(imgVOFuture.get());

        filmDetailVo.setInfo04(infoRequstVO);
        return ResponseVo.success("http://img.meetingshop.cn/",filmDetailVo);
    }
}
