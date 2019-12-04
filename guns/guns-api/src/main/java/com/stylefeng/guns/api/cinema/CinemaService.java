package com.stylefeng.guns.api.cinema;

import com.stylefeng.guns.api.cinema.vo.*;

public interface CinemaService {
    BaseResVO getCondition(Integer brandId, Integer hallType, Integer areaId);

    BaseResVO getCinemas(Integer brandId, Integer hallType, Integer areaId, Integer pageSize, Integer nowPage);

    BaseResVO<GetFieldsDataResVO> getFields(Integer cinemaId);

    BaseResVO<GetFieldInfoDataResVO> getFieldInfo(Integer cinemaId, Integer fieldId);

    MtimeCinema queryCinemaById(Integer cinemaId);

    MtimeHallFilmInfo queryHallFilmInfoById(Integer filmId);

    MtimeField queryFieldById(Integer fieldId);

    MtimeHallDict queryHallById(Integer hallId);
}
