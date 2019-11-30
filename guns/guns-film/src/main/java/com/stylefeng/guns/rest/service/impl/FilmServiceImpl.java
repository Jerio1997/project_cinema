package com.stylefeng.guns.rest.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.config.support.Parameter;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.film.vo.*;
import com.stylefeng.guns.rest.common.persistence.dao.*;
import com.stylefeng.guns.api.film.FilmService;
import com.stylefeng.guns.rest.common.persistence.model.*;
import com.stylefeng.guns.rest.common.persistence.model.MtimeActorT;
import com.stylefeng.guns.rest.common.persistence.model.MtimeBannerT;
import com.stylefeng.guns.rest.common.persistence.model.MtimeFilmInfoT;
import com.stylefeng.guns.rest.common.persistence.model.MtimeFilmT;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Author Jerio
 * CreateTime 2019/11/27 17:48
 **/

@Component
@Service(interfaceClass = FilmService.class,loadbalance = "roundrobin")
public class FilmServiceImpl implements FilmService {

    @Autowired
    private MtimeFilmTMapper mtimeFilmTMapper;
    @Autowired
    private MtimeBannerTMapper mtimeBannerTMapper;
    @Autowired
    private MtimeFilmInfoTMapper mtimeFilmInfoTMapper;
    @Autowired
    private MtimeActorTMapper mtimeActorTMapper;
    @Autowired
    private MtimeCatDictTMapper mtimeCatDictTMapper;
    @Autowired
    private MtimeSourceDictTMapper mtimeSourceDictTMapper;
    @Autowired
    private MtimeYearDictTMapper mtimeYearDictTMapper;


    @Override
    public List<BannerVO> getBanners() {
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("is_valid",0);
        List<MtimeBannerT> mtimeBannerTs = mtimeBannerTMapper.selectList(wrapper);
        List<BannerVO> banners = conert2ListBannerVO(mtimeBannerTs);
        return banners;
    }

    private List<BannerVO> conert2ListBannerVO(List<MtimeBannerT> mtimeBannerTs) {
        List<BannerVO> banners = new ArrayList<>();
        if(CollectionUtils.isEmpty(mtimeBannerTs)){
            return banners;
        }

        for (MtimeBannerT mtimeBannerT : mtimeBannerTs) {
            BannerVO banner = new BannerVO();
            banner.setBannerId(mtimeBannerT.getUuid().toString());
            banner.setBannerAddress(mtimeBannerT.getBannerAddress());
            banner.setBannerUrl(mtimeBannerT.getBannerUrl());
            banners.add(banner);
        }
        return banners;
    }

    @Override
    public FilmVO getHotFilms(boolean isLimit, int nums) {
        FilmVO hotFilms = new FilmVO();
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("film_status",1);
        Integer count = mtimeFilmTMapper.selectCount(wrapper);
        hotFilms.setFilmNum(count);
        List<MtimeFilmT> mtimeFilmTS;
        if(isLimit) {
            Page page = new Page(1, nums);
            mtimeFilmTS = mtimeFilmTMapper.selectPage(page,wrapper);
        }else{
            mtimeFilmTS = mtimeFilmTMapper.selectList(wrapper);
        }
        List<FilmInfo> filmInfos = convert2HotFilmInfo(mtimeFilmTS);
        hotFilms.setFilmInfo(filmInfos);
        return hotFilms;
    }


    private List<FilmInfo> convert2HotFilmInfo(List<MtimeFilmT> mtimeFilmTS) {
        List<FilmInfo> filmInfos = new ArrayList<>();
        if(CollectionUtils.isEmpty(mtimeFilmTS)){
            return filmInfos;
        }

        for (MtimeFilmT mtimeFilmT : mtimeFilmTS) {
            FilmInfo filmInfo = new FilmInfo();
            filmInfo.setFilmId(mtimeFilmT.getUuid().toString());
            filmInfo.setFilmName(mtimeFilmT.getFilmName());
            filmInfo.setImgAddress(mtimeFilmT.getImgAddress());
            filmInfo.setFilmType(mtimeFilmT.getFilmType());
            filmInfo.setFilmScore(mtimeFilmT.getFilmScore());
            filmInfos.add(filmInfo);
        }
        return filmInfos;
    }

    @Override
    public FilmVO getSoonFilms(boolean isLimit, int nums) {
        FilmVO soonFilms = new FilmVO();
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("film_status",2);
        Integer count = mtimeFilmTMapper.selectCount(wrapper);
        soonFilms.setFilmNum(count);
        List<MtimeFilmT> mtimeFilmTS;
        if(isLimit) {
            Page page = new Page(1, nums);
            mtimeFilmTS = mtimeFilmTMapper.selectPage(page,wrapper);
        }else{
            mtimeFilmTS = mtimeFilmTMapper.selectList(wrapper);
        }
        List<FilmInfo> filmInfos = convert2SoonFilmInfo(mtimeFilmTS);
        soonFilms.setFilmInfo(filmInfos);
        return soonFilms;
    }

    private List<FilmInfo> convert2SoonFilmInfo(List<MtimeFilmT> mtimeFilmTS) {
        List<FilmInfo> filmInfos = new ArrayList<>();
        if(CollectionUtils.isEmpty(mtimeFilmTS)){
            return filmInfos;
        }

        for (MtimeFilmT mtimeFilmT : mtimeFilmTS) {
            FilmInfo filmInfo = new FilmInfo();
            filmInfo.setFilmId(mtimeFilmT.getUuid().toString());
            filmInfo.setFilmName(mtimeFilmT.getFilmName());
            filmInfo.setImgAddress(mtimeFilmT.getImgAddress());
            filmInfo.setFilmType(mtimeFilmT.getFilmType());
            filmInfo.setExpectNum(mtimeFilmT.getFilmPresalenum());
            filmInfo.setShowTime(mtimeFilmT.getFilmTime().toString());
            filmInfos.add(filmInfo);
        }
        return filmInfos;

    }

    @Override
    public List<FilmInfo> getBoxRanking(Integer count) {

        Page<MtimeFilmT> page = new Page<>(1,count,"film_box_office",false);
        EntityWrapper<MtimeFilmT> wrapper = new EntityWrapper<>();
        List<MtimeFilmT> mtimeFilmTS = mtimeFilmTMapper.selectPage(page, wrapper);
        List<FilmInfo> filmInfos = convert2BoxRanking(mtimeFilmTS);
        return filmInfos;
    }

    private List<FilmInfo> convert2BoxRanking(List<MtimeFilmT> mtimeFilmTS) {
        List<FilmInfo> filmInfos = new ArrayList<FilmInfo>();
        if(CollectionUtils.isEmpty(mtimeFilmTS)){
            return filmInfos;
        }

        for (MtimeFilmT mtimeFilmT : mtimeFilmTS) {
            FilmInfo filmInfo = new FilmInfo();
            filmInfo.setFilmId(mtimeFilmT.getUuid().toString());
            filmInfo.setFilmName(mtimeFilmT.getFilmName());
            filmInfo.setImgAddress(mtimeFilmT.getImgAddress());
            filmInfo.setBoxNum(mtimeFilmT.getFilmBoxOffice());
            filmInfos.add(filmInfo);
        }
        return filmInfos;
    }

    @Override
    public List<FilmInfo> getExpectRanking(Integer count) {

        Page<MtimeFilmT> page = new Page<>(1,count,"film_preSaleNum",false);
        EntityWrapper<MtimeFilmT> wrapper = new EntityWrapper<>();
        List<MtimeFilmT> mtimeFilmTS = mtimeFilmTMapper.selectPage(page, wrapper);
        List<FilmInfo> filmInfos = convert2ExpectRanking(mtimeFilmTS);
        return filmInfos;
    }

    private List<FilmInfo> convert2ExpectRanking(List<MtimeFilmT> mtimeFilmTS) {
        List<FilmInfo> filmInfos = new ArrayList<FilmInfo>();
        if(CollectionUtils.isEmpty(mtimeFilmTS)){
            return filmInfos;
        }

        for (MtimeFilmT mtimeFilmT : mtimeFilmTS) {
            FilmInfo filmInfo = new FilmInfo();
            filmInfo.setFilmId(mtimeFilmT.getUuid().toString());
            filmInfo.setFilmName(mtimeFilmT.getFilmName());
            filmInfo.setImgAddress(mtimeFilmT.getImgAddress());
            filmInfo.setExpectNum(mtimeFilmT.getFilmPresalenum());
            filmInfos.add(filmInfo);
        }
        return filmInfos;
    }

    @Override
    public List<FilmInfo> getTop(Integer count) {
        Page<MtimeFilmT> page = new Page<>(1,count,"film_score",false);
        EntityWrapper<MtimeFilmT> wrapper = new EntityWrapper<>();
        List<MtimeFilmT> mtimeFilmTS = mtimeFilmTMapper.selectPage(page, wrapper);
        List<FilmInfo> filmInfos = convert2Top(mtimeFilmTS);
        return filmInfos;
    }

    private List<FilmInfo> convert2Top(List<MtimeFilmT> mtimeFilmTS) {
        List<FilmInfo> filmInfos = new ArrayList<FilmInfo>();
        if(CollectionUtils.isEmpty(mtimeFilmTS)){
            return filmInfos;
        }

        for (MtimeFilmT mtimeFilmT : mtimeFilmTS) {
            FilmInfo filmInfo = new FilmInfo();
            filmInfo.setFilmId(mtimeFilmT.getUuid().toString());
            filmInfo.setFilmName(mtimeFilmT.getFilmName());
            filmInfo.setImgAddress(mtimeFilmT.getImgAddress());
            filmInfo.setScore(mtimeFilmT.getFilmScore());
            filmInfos.add(filmInfo);
        }
        return filmInfos;
    }

    //获取影片信息
    private List<GetFilmInfo> getFilmInfos(List<MtimeFilmT> mtimeFilmTS){
        List<GetFilmInfo> filmInfos = new ArrayList<>();
        for (MtimeFilmT mtimeFilmT : mtimeFilmTS) {
            GetFilmInfo getFilmInfo = new GetFilmInfo();
            getFilmInfo.setFilmId(mtimeFilmT.getUuid()+"");
            getFilmInfo.setFilmName(mtimeFilmT.getFilmName());
            getFilmInfo.setFilmScore(mtimeFilmT.getFilmScore());
            getFilmInfo.setFilmType(mtimeFilmT.getFilmType());
            getFilmInfo.setImgAddress(mtimeFilmT.getImgAddress());

            filmInfos.add(getFilmInfo);
        }
        return filmInfos;
    }
    //热映
    @Override
    public GetFilmVo getHotFilm(boolean b, Integer pageSize, Integer nowPage, Integer sortId, Integer sourceId, Integer yearId, Integer catId) {
        GetFilmVo getFilmVo = new GetFilmVo();
        List<GetFilmInfo> filmInfos = new ArrayList<>();

        EntityWrapper<MtimeFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status","1");
        //判断是否是首页需要的内容
        if (b){

            Page<MtimeFilmT> page = new Page<>(1,pageSize);
            List<MtimeFilmT> mtimeFilmTS = mtimeFilmTMapper.selectPage(page,entityWrapper);

            filmInfos = getFilmInfos(mtimeFilmTS);
            getFilmVo.setFilmNum(mtimeFilmTS.size());
            getFilmVo.setGetFilmInfoList(filmInfos);
        }else {
            Page<MtimeFilmT> page = null;
            //根据sortId的不同来搜索影片,排序方式，1-按热门搜索，2-按时间搜索，3-按评价搜索
            if (sortId == 1){
                page = new Page<>(nowPage,pageSize,"film_box_office");
            }
            if (sortId == 2){
                page = new Page<>(nowPage,pageSize,"film_time");
            }
            if (sortId == 3){
                page = new Page<>(nowPage,pageSize,"film_score");
            }
            else {
                page = new Page<>(nowPage,pageSize,"film_box_office");
            }

            //catId,sourceId,yearId不为默认(99),则根据对应的编号进行查询
            if (sortId != 99){
                entityWrapper.eq("film_source",sourceId);
            }
            if (catId != 99){
                //影片分类，参照分类表,多个分类以#分割
                String cat = "%#" + catId + "#%";
                entityWrapper.eq("film_cats",cat);
            }
            if (yearId != 99){
                entityWrapper.eq("film_date",yearId);
            }
            List<MtimeFilmT> mtimeFilmTS = mtimeFilmTMapper.selectPage(page,entityWrapper);

            filmInfos = getFilmInfos(mtimeFilmTS);
            getFilmVo.setFilmNum(mtimeFilmTS.size());
            //totalCounts/pageSize
            int totalCounts = mtimeFilmTMapper.selectCount(entityWrapper);
            int totalPages = (totalCounts/pageSize) + 1;

            getFilmVo.setGetFilmInfoList(filmInfos);
            getFilmVo.setTotalPage(totalPages);
            getFilmVo.setNowPage(nowPage);
        }
        return getFilmVo;
    }

    //即将上映
    @Override
    public GetFilmVo getSoonFilm(boolean b, Integer pageSize, Integer nowPage, Integer sortId, Integer sourceId, Integer yearId, Integer catId) {
        GetFilmVo getFilmVo = new GetFilmVo();
        List<GetFilmInfo> filmInfos = new ArrayList<>();

        EntityWrapper<MtimeFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status","2");
        //判断是否是首页需要的内容
        if (b){

            Page<MtimeFilmT> page = new Page<>(1,pageSize);
            List<MtimeFilmT> mtimeFilmTS = mtimeFilmTMapper.selectPage(page,entityWrapper);

            filmInfos = getFilmInfos(mtimeFilmTS);
            getFilmVo.setFilmNum(mtimeFilmTS.size());
            getFilmVo.setGetFilmInfoList(filmInfos);
        }else {
            Page<MtimeFilmT> page = null;
            //根据sortId的不同来搜索影片,排序方式，1-按热门搜索，2-按时间搜索，3-按评价搜索
            if (sortId == 1){
                page = new Page<>(nowPage,pageSize,"film_preSaleNum");
            }
            if (sortId == 2){
                page = new Page<>(nowPage,pageSize,"film_time");
            }
            if (sortId == 3){
                page = new Page<>(nowPage,pageSize,"film_preSaleNum");
            }
            else {
                page = new Page<>(nowPage,pageSize,"film_preSaleNum");
            }

            //catId,sourceId,yearId不为默认(99),则根据对应的编号进行查询
            if (sortId != 99){
                entityWrapper.eq("film_source",sourceId);
            }
            if (catId != 99){
                //影片分类，参照分类表,多个分类以#分割
                String cat = "%#" + catId + "#%";
                entityWrapper.eq("film_cats",cat);
            }
            if (yearId != 99){
                entityWrapper.eq("film_date",yearId);
            }
            List<MtimeFilmT> mtimeFilmTS = mtimeFilmTMapper.selectPage(page,entityWrapper);

            filmInfos = getFilmInfos(mtimeFilmTS);
            getFilmVo.setFilmNum(mtimeFilmTS.size());
            //totalCounts/pageSize
            int totalCounts = mtimeFilmTMapper.selectCount(entityWrapper);
            int totalPages = (totalCounts/pageSize) + 1;

            getFilmVo.setGetFilmInfoList(filmInfos);
            getFilmVo.setTotalPage(totalPages);
            getFilmVo.setNowPage(nowPage);
        }
        return getFilmVo;
    }

    //经典影片
    @Override
    public GetFilmVo getClassicFilm(boolean b, Integer pageSize, Integer nowPage, Integer sortId, Integer sourceId, Integer yearId, Integer catId) {
        GetFilmVo getFilmVo = new GetFilmVo();
        List<GetFilmInfo> filmInfos = new ArrayList<>();

        EntityWrapper<MtimeFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status","3");

        Page<MtimeFilmT> page = null;
        //根据sortId的不同来搜索影片,排序方式，1-按热门搜索，2-按时间搜索，3-按评价搜索
        if (sortId == 1){
            page = new Page<>(nowPage,pageSize,"film_box_office");
        }
        if (sortId == 2){
            page = new Page<>(nowPage,pageSize,"film_time");
        }
        if (sortId == 3){
            page = new Page<>(nowPage,pageSize,"film_score");
        }
        else {
            page = new Page<>(nowPage,pageSize,"film_box_office");
        }

        //catId,sourceId,yearId不为默认(99),则根据对应的编号进行查询
        if (sortId != 99){
            entityWrapper.eq("film_source",sourceId);
        }
        if (catId != 99){
            //影片分类，参照分类表,多个分类以#分割
            String cat = "%#" + catId + "#%";
            entityWrapper.eq("film_cats",cat);
        }
        if (yearId != 99){
            entityWrapper.eq("film_date",yearId);
        }
        List<MtimeFilmT> mtimeFilmTS = mtimeFilmTMapper.selectPage(page,entityWrapper);

        filmInfos = getFilmInfos(mtimeFilmTS);
        getFilmVo.setFilmNum(mtimeFilmTS.size());
        //totalCounts/pageSize
        int totalCounts = mtimeFilmTMapper.selectCount(entityWrapper);
        int totalPages = (totalCounts/pageSize) + 1;

        getFilmVo.setGetFilmInfoList(filmInfos);
        getFilmVo.setTotalPage(totalPages);
        getFilmVo.setNowPage(nowPage);

        return getFilmVo;
    }

    @Override
    public FilmDetailVo getFilmDetail(int searchType, String searchFilm) {
        FilmDetailVo filmDetailVo = null;
        //searchType : ‘0表示按照编号查找，1表示按照名称查找’
        if (searchType == 1){
            filmDetailVo = mtimeFilmTMapper.getFilmDetailByName("%"+searchFilm+"%");
        }else {
            filmDetailVo = mtimeFilmTMapper.getFilmDetailById(searchFilm);
        }
        return filmDetailVo;
    }

    private MtimeFilmInfoT getFilmInfo(String filmId){

        MtimeFilmInfoT mtimeFilmInfoT = new MtimeFilmInfoT();
        mtimeFilmInfoT.setFilmId(filmId);

        mtimeFilmInfoT = mtimeFilmInfoTMapper.selectOne(mtimeFilmInfoT);

        return mtimeFilmInfoT;
    }

    @Override
    public FilmDescVO getFilmDesc(String filmId) {

        MtimeFilmInfoT mtimeFilmInfoT = getFilmInfo(filmId);

        FilmDescVO filmDescVO = new FilmDescVO();
        filmDescVO.setBiography(mtimeFilmInfoT.getBiography());
        filmDescVO.setFilmId(filmId);

        return filmDescVO;
    }

    @Override
    public ImgVO getImgs(String filmId) {

        MtimeFilmInfoT mtimeFilmInfoT = getFilmInfo(filmId);
        String filmImgs = mtimeFilmInfoT.getFilmImgs();
        String[] filmImgs1 = filmImgs.split(",");

        ImgVO imgVO = new ImgVO();
        imgVO.setMainImg(filmImgs1[0]);
        imgVO.setImg01(filmImgs1[1]);
        imgVO.setImg02(filmImgs1[2]);
        imgVO.setImg03(filmImgs1[3]);
        imgVO.setImg04(filmImgs1[4]);
        return imgVO;
    }

    @Override
    public ActorVO getDectInfo(String filmId) {
        MtimeFilmInfoT mtimeFilmInfoT = new MtimeFilmInfoT();

        Integer directorId = mtimeFilmInfoT.getDirectorId();

        MtimeActorT mtimeActorT = mtimeActorTMapper.selectById(filmId);

        ActorVO actorVO = new ActorVO();
        actorVO.setDirectorName(mtimeActorT.getActorName());
        actorVO.setImgAddress(mtimeActorT.getActorImg());

        return actorVO;
    }

    @Override
    public List<ActorVO> getActors(String filmId) {

        List<ActorVO> actorVOS = mtimeActorTMapper.getActors(filmId);
        return actorVOS;
    }

    @Override
    public List<CatVO> getCats(String catId) {
        EntityWrapper<MtimeCatDictT> wrapper = new EntityWrapper<>();
        List<MtimeCatDictT> mtimeCatDictTS = mtimeCatDictTMapper.selectList(wrapper);
        List<CatVO> catVOList = convert2CatVOList(mtimeCatDictTS, catId);
        return catVOList;
    }

    private List<CatVO> convert2CatVOList(List<MtimeCatDictT> mtimeCatDictTS, String catId) {
        List<CatVO> catVOList = new ArrayList<>();
        if (CollectionUtils.isEmpty(mtimeCatDictTS)) {
            return catVOList;
        }

        String uuid = null;
        for (MtimeCatDictT mtimeCatDictT : mtimeCatDictTS) {
            CatVO catVO = new CatVO();
            uuid = mtimeCatDictT.getUuid().toString();
            catVO.setCatId(uuid);
            catVO.setCatName(mtimeCatDictT.getShowName());
            if (uuid.equals(catId)) {
                catVO.setActive(true);
            } else {
                catVO.setActive(false);
            }
            catVOList.add(catVO);
        }
        return catVOList;
    }

    @Override
    public List<SourceVO> getSource(String sourceId) {
        EntityWrapper<MtimeSourceDictT> wrapper = new EntityWrapper<>();
        List<MtimeSourceDictT> mtimeSourceDictTS = mtimeSourceDictTMapper.selectList(wrapper);
        List<SourceVO> sourceVOList = convert2SourceVOList(mtimeSourceDictTS, sourceId);
        return sourceVOList;
    }

    private List<SourceVO> convert2SourceVOList(List<MtimeSourceDictT> mtimeSourceDictTS, String sourceId) {

        List<SourceVO> sourceVOList = new ArrayList<>();
        if (CollectionUtils.isEmpty(mtimeSourceDictTS)) {
            return sourceVOList;
        }

        String uuid = null;
        for (MtimeSourceDictT mtimeSourceDictT : mtimeSourceDictTS) {
            SourceVO sourceVO = new SourceVO();
            uuid = mtimeSourceDictT.getUuid().toString();
            sourceVO.setSourceId(uuid);
            sourceVO.setSourceName(mtimeSourceDictT.getShowName());
            if(uuid.equals(sourceId)){
                sourceVO.setActive(true);
            }else {
                sourceVO.setActive(false);
            }
            sourceVOList.add(sourceVO);
        }
        return sourceVOList;
    }

    @Override
    public List<YearVO> getYears(String yearId) {
        EntityWrapper<MtimeYearDictT> wrapper = new EntityWrapper<>();
        List<MtimeYearDictT> mtimeYearDictTS = mtimeYearDictTMapper.selectList(wrapper);
        List<YearVO> yearVOList = convert2YearVOList(mtimeYearDictTS, yearId);
        return yearVOList;
    }

    private List<YearVO> convert2YearVOList(List<MtimeYearDictT> mtimeYearDictTS, String yearId) {

        List<YearVO> yearVOList = new ArrayList<>();
        if(CollectionUtils.isEmpty(mtimeYearDictTS)){
            return yearVOList;
        }
        String uuid = null;

        for (MtimeYearDictT mtimeYearDictT : mtimeYearDictTS) {
            YearVO yearVO = new YearVO();
            uuid = mtimeYearDictT.getUuid().toString();
            yearVO.setYearId(uuid);
            yearVO.setYearName(mtimeYearDictT.getShowName());
            if(uuid.equals(yearId)){
                yearVO.setActive(true);
            }else {
                yearVO.setActive(false);
            }
            yearVOList.add(yearVO);
        }
        return yearVOList;
    }
}
