package com.stylefeng.guns.rest.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.cinema.CinemaService;
import com.stylefeng.guns.api.cinema.vo.BaseResVO;
import com.stylefeng.guns.api.cinema.vo.ConditionResVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("cinema")
public class CinemaController {

    @Reference(interfaceClass = CinemaService.class, check = false)
    CinemaService cinemaService;

    @RequestMapping("getCondition")
    public BaseResVO<ConditionResVO> getCondition(Integer brandId, Integer hallType, Integer areaId) {
        BaseResVO baseResVO = cinemaService.getCondition(brandId, hallType, areaId);
        return baseResVO;
    }

    @RequestMapping("getCinemas")
    public BaseResVO<List> getCinemas(Integer brandId, Integer hallType, Integer areaId, Integer pageSize, Integer nowPage) {
        BaseResVO cinemas = cinemaService.getCinemas(brandId, hallType, areaId, pageSize, nowPage);
        return cinemas;
    }

}
