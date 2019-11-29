/*
package com.stylefeng.guns.rest.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.film.GetFilmsAndFilmsService;
import com.stylefeng.guns.api.film.vo.GetFilmInfo;
import com.stylefeng.guns.api.film.vo.GetFilmVo;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeFilmTMapper;
import com.stylefeng.guns.rest.common.persistence.model.MtimeFilmT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Service(interfaceClass = GetFilmsAndFilmsService.class)

public class GetFilmsAndFilmsServiceImpl implements GetFilmsAndFilmsService {
    @Autowired
    private MtimeFilmTMapper mtimeFilmTMapper;

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
}
*/
