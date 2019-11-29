package com.stylefeng.guns.rest.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.cinema.CinemaService;
import com.stylefeng.guns.api.cinema.vo.BaseResVO;
import com.stylefeng.guns.api.cinema.vo.GetFieldsDataResVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cinema")
public class CinemaController {
    @Reference(interfaceClass = CinemaService.class)
    CinemaService cinemaService;
    @RequestMapping("getFields")
    public BaseResVO<GetFieldsDataResVO> getFields(Integer cinemaId){
        BaseResVO<GetFieldsDataResVO> fields = cinemaService.getFields(cinemaId);
        return fields;
    }

}
