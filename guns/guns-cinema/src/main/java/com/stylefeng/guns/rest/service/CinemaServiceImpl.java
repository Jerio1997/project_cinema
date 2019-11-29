package com.stylefeng.guns.rest.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.api.cinema.CinemaService;
import com.stylefeng.guns.api.cinema.vo.BaseResVO;
import com.stylefeng.guns.api.cinema.vo.GetFieldsDataResVO;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeCinemaTMapper;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeFieldTMapper;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeHallFilmInfoTMapper;
import com.stylefeng.guns.rest.common.persistence.model.MtimeCinemaT;
import com.stylefeng.guns.rest.common.persistence.model.MtimeFieldT;
import com.stylefeng.guns.rest.common.persistence.model.MtimeHallFilmInfoT;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Component
@Service(interfaceClass = CinemaService.class)
public class CinemaServiceImpl implements CinemaService {
    @Autowired
    MtimeCinemaTMapper mtimeCinemaTMapper;
    @Autowired
    MtimeFieldTMapper mtimeFieldTMapper;
    @Autowired
    MtimeHallFilmInfoTMapper mtimeHallFilmInfoTMapper;
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
