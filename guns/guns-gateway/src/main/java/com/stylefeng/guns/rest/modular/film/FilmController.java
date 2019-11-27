package com.stylefeng.guns.rest.modular.film;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.rest.film.FilmService;
import com.stylefeng.guns.rest.film.vo.FilmVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author Jerio
 * CreateTime 2019/11/27 21:31
 **/
@RestController
@RequestMapping("film")
public class FilmController {

    @Reference(interfaceClass = FilmService.class)
    private FilmService filmService;

    @RequestMapping("query")
    public FilmVo queryById(Integer id){
        FilmVo vo = filmService.getById(id);
        return vo;
    }
}
