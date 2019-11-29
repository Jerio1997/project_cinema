package com.stylefeng.guns.rest.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.cinema.CinemaService;
import com.stylefeng.guns.api.cinema.vo.BaseResVO;
import com.stylefeng.guns.api.cinema.vo.ConditionResVO;
import com.stylefeng.guns.api.cinema.vo.GetFieldInfoDataResVO;
import com.stylefeng.guns.api.cinema.vo.GetFieldsDataResVO;

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
    @RequestMapping("getFields")
    public BaseResVO<GetFieldsDataResVO> getFields(Integer cinemaId){
        BaseResVO<GetFieldsDataResVO> fields = cinemaService.getFields(cinemaId);
        return fields;
    }
    @RequestMapping("getFieldInfo")
    public BaseResVO<GetFieldInfoDataResVO> getFieldInfo(Integer cinemaId,Integer fieldId){
        BaseResVO<GetFieldInfoDataResVO> fieldInfo = cinemaService.getFieldInfo(cinemaId, fieldId);
        return fieldInfo;
    }
}
