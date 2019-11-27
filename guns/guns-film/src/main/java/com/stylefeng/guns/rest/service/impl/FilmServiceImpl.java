package com.stylefeng.guns.rest.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeFilmTMapper;
import com.stylefeng.guns.rest.common.persistence.model.MtimeFilmT;
import com.stylefeng.guns.rest.film.FilmService;
import com.stylefeng.guns.rest.film.vo.FilmVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Author Jerio
 * CreateTime 2019/11/27 17:48
 **/

@Component
@Service(interfaceClass = FilmService.class)
public class FilmServiceImpl implements FilmService {

    @Autowired
    private MtimeFilmTMapper mtimeFilmTMapper;

    @Override
    public FilmVo getById(Integer id) {
        MtimeFilmT mtimeFilmT = mtimeFilmTMapper.selectById(id);
        FilmVo filmVo = convert2FilmVo(mtimeFilmT);
        return filmVo;
    }

    private FilmVo convert2FilmVo(MtimeFilmT mtimeFilmT) {
        FilmVo filmVo = new FilmVo();
        if(mtimeFilmT == null){
            return filmVo;
        }
//        filmVo.setUuid(mtimeFilmT.getUuid());
//        filmVo.setFilmName(mtimeFilmT.getFilmName());
        BeanUtils.copyProperties(mtimeFilmT,filmVo);
        return filmVo;
    }
}
