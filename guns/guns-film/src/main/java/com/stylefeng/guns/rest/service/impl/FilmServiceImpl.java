package com.stylefeng.guns.rest.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeFilmTMapper;
import com.stylefeng.guns.rest.common.persistence.model.MtimeFilmT;
import com.stylefeng.guns.rest.film.FilmService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Author Jerio
 * CreateTime 2019/11/27 17:48
 **/

@Component
@Service(interfaceClass = FilmService.class,loadbalance = "roundrobin")
public class FilmServiceImpl implements FilmService {

    @Autowired
    private MtimeFilmTMapper mtimeFilmTMapper;

}
