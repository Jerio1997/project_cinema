package com.stylefeng.guns.rest.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
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
        return null;
    }

    @Override
    public FilmVO getSoonFilms(boolean isLimit, int nums) {
        return null;
    }

    @Override
    public List<FilmInfo> getBoxRanking() {
        return null;
    }

    @Override
    public List<FilmInfo> getExpectRanking() {
        return null;
    }

    @Override
    public List<FilmInfo> getTop() {
        return null;
    }
}
