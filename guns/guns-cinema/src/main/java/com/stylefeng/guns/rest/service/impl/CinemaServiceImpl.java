package com.stylefeng.guns.rest.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.cinema.CinemaService;
import com.stylefeng.guns.api.cinema.vo.BaseResVO;
import com.stylefeng.guns.api.cinema.vo.CinemasConditionResVo;
import com.stylefeng.guns.api.cinema.vo.ConditionResVO;
import com.stylefeng.guns.api.cinema.vo.GetFieldsDataResVO;
import com.stylefeng.guns.rest.common.persistence.dao.*;
import com.stylefeng.guns.rest.common.persistence.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


@Component
@Service(interfaceClass = CinemaService.class, loadbalance = "roundrobin")
public class CinemaServiceImpl implements CinemaService {

    @Autowired
    MtimeAreaDictTMapper mtimeAreaDictTMapper;

    @Autowired
    MtimeBrandDictTMapper mtimeBrandDictTMapper;

    @Autowired
    MtimeHallDictTMapper mtimeHallDictTMapper;

    @Autowired
    MtimeCinemaTMapper mtimeCinemaTMapper;

    @Autowired
    MtimeFieldTMapper mtimeFieldTMapper;

    @Autowired
    MtimeHallFilmInfoTMapper mtimeHallFilmInfoTMapper;

    @Override
    public BaseResVO<ConditionResVO> getCondition(Integer brandId, Integer hallType, Integer areaId) {
        if (brandId == null) {
            brandId = 99;
        }
        if (hallType == null) {
            hallType = 99;
        }
        if (areaId == null) {
            areaId = 99;
        }
        BaseResVO<ConditionResVO> baseResVO = new BaseResVO();
        ConditionResVO conditionResVO = new ConditionResVO();

        EntityWrapper<MtimeAreaDictT> areaWrapper = new EntityWrapper<>();

        List<MtimeAreaDictT> areaList = mtimeAreaDictTMapper.selectList(areaWrapper);
        List<ConditionResVO.AreaListBean> areaListBeanList = new ArrayList<>();
        for (MtimeAreaDictT mtimeAreaDictT : areaList) {
            ConditionResVO.AreaListBean areaListBean = new ConditionResVO.AreaListBean();
            areaListBean.setActive(false);
            areaListBean.setAreaId(mtimeAreaDictT.getUuid());
            areaListBean.setAreaName(mtimeAreaDictT.getShowName());
            if (mtimeAreaDictT.getUuid().equals(areaId)) {
                areaListBean.setActive(true);
            }
            areaListBeanList.add(areaListBean);
        }
        conditionResVO.setAreaList(areaListBeanList);

         EntityWrapper<MtimeBrandDictT> brandWrapper = new EntityWrapper<>();
        List<MtimeBrandDictT> brandList = mtimeBrandDictTMapper.selectList(brandWrapper);
        List<ConditionResVO.BrandListBean> brandListBeanList = new ArrayList<>();
        for (MtimeBrandDictT mtimeBrandDictT : brandList) {
            ConditionResVO.BrandListBean brandListBean = new ConditionResVO.BrandListBean();
            brandListBean.setActive(false);
            brandListBean.setBrandId(mtimeBrandDictT.getUuid());
            brandListBean.setBrandName(mtimeBrandDictT.getShowName());
            if (mtimeBrandDictT.getUuid().equals(brandId)) {
                brandListBean.setActive(true);
            }
            brandListBeanList.add(brandListBean);
        }
        conditionResVO.setBrandList(brandListBeanList);

        EntityWrapper<MtimeHallDictT> hallWrapper = new EntityWrapper<>();
        List<MtimeHallDictT> hallList = mtimeHallDictTMapper.selectList(hallWrapper);
        List<ConditionResVO.HalltypeListBean> halltypeListBeanList = new ArrayList<>();
        for (MtimeHallDictT mtimeHallDictT : hallList) {
            ConditionResVO.HalltypeListBean halltypeListBean = new ConditionResVO.HalltypeListBean();
            halltypeListBean.setActive(false);
            halltypeListBean.setHalltypeId(mtimeHallDictT.getUuid());
            halltypeListBean.setHalltypeName(mtimeHallDictT.getShowName());
            if (mtimeHallDictT.getUuid().equals(hallType)) {
                halltypeListBean.setActive(true);
            }
            halltypeListBeanList.add(halltypeListBean);
        }
        conditionResVO.setHalltypeList(halltypeListBeanList);

        baseResVO.setData(conditionResVO);
        baseResVO.setImgPre("");
        baseResVO.setMsg("");
        baseResVO.setStatus(0);
        return baseResVO;
    }

    @Override
    public BaseResVO getCinemas(Integer brandId, Integer hallType, Integer areaId, Integer pageSize, Integer nowPage) {
        if (brandId == null) {
            brandId = 99;
        }
        if (hallType == null) {
            hallType = 99;
        }
        if (areaId == null) {
            areaId = 99;
        }
        if (pageSize == null) {
            pageSize = 12;
        }
        if (nowPage == null) {
            nowPage = 1;
        }
        BaseResVO<List> baseResVO = new BaseResVO<>();

        EntityWrapper<MtimeCinemaT> cinematWrapper = new EntityWrapper<>();
        if (brandId != 99) {
            cinematWrapper.eq("brand_id", brandId);
        }
        if (areaId != 99) {
            cinematWrapper.eq("area_id", areaId);
        }

        if (hallType != 99) {
            cinematWrapper.like("hall_ids", "#"+hallType+"#");
        }

//        Page<MtimeCinemaT> page = new Page<>(nowPage, pageSize);
        List<MtimeCinemaT> mtimeCinemaTS = mtimeCinemaTMapper.selectList(cinematWrapper);
        List<CinemasConditionResVo> cinemasConditionResVoList = new ArrayList<>();
        for (MtimeCinemaT mtimeCinemaT : mtimeCinemaTS) {
            CinemasConditionResVo cinemasConditionResVo = new CinemasConditionResVo();
            cinemasConditionResVo.setCinemaAddress(mtimeCinemaT.getCinemaAddress());
            cinemasConditionResVo.setCinemaName(mtimeCinemaT.getCinemaName());
            cinemasConditionResVo.setMinimumPrice(mtimeCinemaT.getMinimumPrice());
            cinemasConditionResVo.setUuid(mtimeCinemaT.getUuid());
            cinemasConditionResVoList.add(cinemasConditionResVo);
        }
        baseResVO.setData(cinemasConditionResVoList);
        baseResVO.setImgPre("http://img.meetingshop.cn/");
        baseResVO.setMsg("");
        baseResVO.setNowPage(nowPage);
        baseResVO.setStatus(0);
        baseResVO.setTotalPage(1);
        return baseResVO;
    }


    @Override
    public BaseResVO<GetFieldsDataResVO> getFields(Integer cinemaId) {
        BaseResVO<GetFieldsDataResVO> baseResVO = new BaseResVO<>();
        GetFieldsDataResVO getFieldsDataResVO = new GetFieldsDataResVO();
        GetFieldsDataResVO.CinemaInfoBean cinemaInfoBean = new GetFieldsDataResVO.CinemaInfoBean();
        //cinemaInfo
        MtimeCinemaT mtimeCinemaT = mtimeCinemaTMapper.selectById(cinemaId);
        cinemaInfoBean.setCinemaAdress(mtimeCinemaT.getCinemaAddress());
        cinemaInfoBean.setCinemaId(cinemaId);
        cinemaInfoBean.setCinemaName(mtimeCinemaT.getCinemaName());
        cinemaInfoBean.setCinemaPhone(mtimeCinemaT.getCinemaPhone());
        cinemaInfoBean.setImgUrl(mtimeCinemaT.getImgAddress());
        getFieldsDataResVO.setCinemaInfo(cinemaInfoBean);
        //filmList
        EntityWrapper<MtimeFieldT> mtimeFieldTEntityWrapper = new EntityWrapper<>();
        mtimeFieldTEntityWrapper.eq("cinema_id",cinemaId);
        List<MtimeFieldT> mtimeFieldTS = mtimeFieldTMapper.selectList(mtimeFieldTEntityWrapper);
        ArrayList<GetFieldsDataResVO.FilmListBean> filmListBeans = new ArrayList<>();

        if(!CollectionUtils.isEmpty(mtimeFieldTS))
        {
            //get all filmIds
            HashSet<Integer> filmIds = new HashSet<>();
            for (MtimeFieldT mtimeFieldT : mtimeFieldTS) {
                filmIds.add(mtimeFieldT.getFilmId());
            }

            for (Integer filmId : filmIds) {
                GetFieldsDataResVO.FilmListBean filmListBean = new GetFieldsDataResVO.FilmListBean();
                //根据filmId 找 hall_film_Info
                // except filmFields
                EntityWrapper<MtimeHallFilmInfoT> mtimeHallFilmInfoTEntityWrapper = new EntityWrapper<>();
                mtimeHallFilmInfoTEntityWrapper.eq("film_id", filmId);
                List<MtimeHallFilmInfoT> mtimeHallFilmInfoTS = mtimeHallFilmInfoTMapper.selectList(mtimeHallFilmInfoTEntityWrapper);
                MtimeHallFilmInfoT mtimeHallFilmInfoT = mtimeHallFilmInfoTS.get(0);
                BeanUtils.copyProperties(mtimeHallFilmInfoT,filmListBean);
                filmListBean.setFilmType(mtimeHallFilmInfoT.getFilmLanguage());
                // filmFields
                EntityWrapper<MtimeFieldT> mtimeFieldTEntityWrapperForList = new EntityWrapper<>();
                mtimeFieldTEntityWrapperForList.eq("cinema_id",cinemaId).and().eq("film_id",filmId);
                List<MtimeFieldT> mtimeFieldTSForList = mtimeFieldTMapper.selectList(mtimeFieldTEntityWrapperForList);
                ArrayList<GetFieldsDataResVO.FilmListBean.FilmFieldsBean> filmFieldsBeans = new ArrayList<>();
                for (MtimeFieldT mtimeFieldT : mtimeFieldTSForList) {
                    GetFieldsDataResVO.FilmListBean.FilmFieldsBean filmFieldsBean = new GetFieldsDataResVO.FilmListBean.FilmFieldsBean();
                    BeanUtils.copyProperties(mtimeFieldT,filmFieldsBean);
                    filmFieldsBean.setLanguage(mtimeHallFilmInfoT.getFilmLanguage());
                    filmFieldsBean.setFieldId(mtimeFieldT.getUuid());
                    filmFieldsBeans.add(filmFieldsBean);
                }
                filmListBean.setFilmFields(filmFieldsBeans);
                filmListBeans.add(filmListBean);
            }

        }
        getFieldsDataResVO.setFilmList(filmListBeans);
        baseResVO.setData(getFieldsDataResVO);
        baseResVO.setStatus(0);
        baseResVO.setImgPre("http://img.meetingshop.cn/");
        return baseResVO;
    }
}
