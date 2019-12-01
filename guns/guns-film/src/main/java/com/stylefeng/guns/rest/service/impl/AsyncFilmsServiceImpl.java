package com.stylefeng.guns.rest.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.api.film.AsyncFilmsService;
import com.stylefeng.guns.api.film.vo.*;
import com.stylefeng.guns.rest.common.persistence.dao.*;
import com.stylefeng.guns.rest.common.persistence.model.MtimeActorT;
import com.stylefeng.guns.rest.common.persistence.model.MtimeFilmInfoT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Service(interfaceClass = AsyncFilmsService.class,loadbalance = "roundrobin")
public class AsyncFilmsServiceImpl implements AsyncFilmsService {

    @Autowired
    private MtimeFilmInfoTMapper mtimeFilmInfoTMapper;
    @Autowired
    private MtimeActorTMapper mtimeActorTMapper;

    private MtimeFilmInfoT getFilmInfo(String filmId) {

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
        MtimeFilmInfoT mtimeFilmInfoT = getFilmInfo(filmId);

        Integer directorId = mtimeFilmInfoT.getDirectorId();

        MtimeActorT mtimeActorT = mtimeActorTMapper.selectById(directorId);

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
}