package com.stylefeng.guns.rest.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.film.vo.BannerVO;
import com.stylefeng.guns.api.film.vo.FilmInfo;
import com.stylefeng.guns.api.film.vo.FilmVO;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeBannerTMapper;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeFilmTMapper;
import com.stylefeng.guns.api.film.FilmService;
import com.stylefeng.guns.rest.common.persistence.model.MtimeBannerT;
import com.stylefeng.guns.rest.common.persistence.model.MtimeFilmT;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
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
        BannerVO banner = new BannerVO();
        for (MtimeBannerT mtimeBannerT : mtimeBannerTs) {
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
        FilmInfo filmInfo = new FilmInfo();
        for (MtimeFilmT mtimeFilmT : mtimeFilmTS) {
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
        FilmInfo filmInfo = new FilmInfo();
        for (MtimeFilmT mtimeFilmT : mtimeFilmTS) {
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
        FilmInfo filmInfo = new FilmInfo();
        for (MtimeFilmT mtimeFilmT : mtimeFilmTS) {
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
        return null;
    }

    @Override
    public List<FilmInfo> getTop(Integer count) {
        return null;
    }
}
