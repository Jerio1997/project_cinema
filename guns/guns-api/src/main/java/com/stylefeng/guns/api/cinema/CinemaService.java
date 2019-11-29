package com.stylefeng.guns.api.cinema;

import com.stylefeng.guns.api.cinema.vo.BaseResVO;
import com.stylefeng.guns.api.cinema.vo.GetFieldsDataResVO;

public interface CinemaService {
    BaseResVO<GetFieldsDataResVO> getFields(Integer cinemaId);
}
