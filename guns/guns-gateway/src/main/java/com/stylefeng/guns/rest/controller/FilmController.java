package com.stylefeng.guns.rest.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.RpcContext;
import com.stylefeng.guns.api.film.AsyncFilmsService;
import com.stylefeng.guns.api.film.FilmService;
import com.stylefeng.guns.api.film.vo.*;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
@RequestMapping("film")
public class FilmController {

    @Reference(interfaceClass = FilmService.class,check = false)
    private FilmService filmService;

    @Reference(interfaceClass = AsyncFilmsService.class,async = true,check = false)
    private AsyncFilmsService asyncFilmsService;

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
            filmIndexVO.setExpectRanking(expectRanking);
            filmIndexVO.setTop100(top);

        } catch (Exception e) {
            filmRespVO.setStatus(999);
            filmRespVO.setMsg("系统出现异常，请联系管理员");
            return filmRespVO;
        }
        filmRespVO.setData(filmIndexVO);
        filmRespVO.setStatus(0);
        filmRespVO.setImgPre("http://img.meetingshop.cn/");
        filmRespVO.setTotalPage("");

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
    public ResponseVo gitFilms(RequestFilmsVo requestFilmsVo){

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

    @RequestMapping(value = "films/{searchFilm}",method = RequestMethod.GET)
    public ResponseVo films(@PathVariable("searchFilm")String searchFilm,
                            int searchType) throws ExecutionException, InterruptedException {
        //searchType : ‘0表示按照编号查找，1表示按照名称查找'

        FilmDetailVo filmDetailVo = filmService.getFilmDetail(searchType,searchFilm);

        if (filmDetailVo == null){
            return ResponseVo.serviceException("查询失败，无影片可加载");
        }else if (filmDetailVo.getFilmId()==null || filmDetailVo.getFilmId().trim().length() == 0){
            return ResponseVo.serviceException("查询失败，无影片可加载");
        }
       String filmId = filmDetailVo.getFilmId();
      //查询影片的详细信息,使用了dubb0的异步调用
      //获取影片描述信息
      //FilmDescVO filmDescVO = filmService.getFilmDesc(filmId);  //不使用异步调用
        asyncFilmsService.getFilmDesc(filmId);
        //Future设计模式是Java多线程开发常用设计模式。一句话，将客户端请求的处理过程从同步改为异步，
        // 以便将客户端解放出来，在服务端程序处理期间可以去干点其他事情，最后再来取请求的结果。
        // 好处在于整个调用过程中不需要等待，可以充分利用所有的时间片段，提高系统的响应速度。

        Future<FilmDescVO> filmDescVOFuture = RpcContext.getContext().getFuture();
        //图片信息
        asyncFilmsService.getImgs(filmId);
        Future<ImgVO> imgVOFuture = RpcContext.getContext().getFuture();
        //导演信息
        asyncFilmsService.getDectInfo(filmId);
        Future<ActorVO> actorVOFuture = RpcContext.getContext().getFuture();
        //演员信息
        asyncFilmsService.getActors(filmId);
        Future<List<ActorVO>> actors = RpcContext.getContext().getFuture();

        InfoRequstVO infoRequstVO = new InfoRequstVO();
       //组织actor属性
        ActorRequestVO actorRequestVO = new ActorRequestVO();
        actorRequestVO.setActors(actors.get());
        actorRequestVO.setDirector(actorVOFuture.get());
       //组织info对象
        infoRequstVO.setActors(actorRequestVO);
        infoRequstVO.setBiography(filmDescVOFuture.get().getBiography());
        infoRequstVO.setFilmId(filmId);
        infoRequstVO.setImgVO(imgVOFuture.get());

        filmDetailVo.setInfo04(infoRequstVO);
        return ResponseVo.success("http://img.meetingshop.cn/",filmDetailVo);
    }
}
