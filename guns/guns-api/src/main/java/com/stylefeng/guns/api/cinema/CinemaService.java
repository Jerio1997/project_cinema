package com.stylefeng.guns.api.cinema;

import com.stylefeng.guns.api.cinema.vo.BaseResVO;
import com.stylefeng.guns.api.cinema.vo.ConditionResVO;

public interface CinemaService {
    BaseResVO getCondition(Integer brandId, Integer hallType, Integer areaId);
    BaseResVO getCinemas(Integer brandId, Integer hallType, Integer areaId, Integer pageSize, Integer nowPage);
}
