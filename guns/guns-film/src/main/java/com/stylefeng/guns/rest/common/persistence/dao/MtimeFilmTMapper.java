package com.stylefeng.guns.rest.common.persistence.dao;

import com.stylefeng.guns.rest.common.persistence.model.MtimeFilmT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.stylefeng.guns.api.film.vo.FilmDetailVo;

/**
 * <p>
 * 影片主表 Mapper 接口
 * </p>
 *
 * @author Jerio
 * @since 2019-11-28
 */
public interface MtimeFilmTMapper extends BaseMapper<MtimeFilmT> {

    FilmDetailVo getFilmDetailByName(/*@Param("filmName")*/ String filmName);

    FilmDetailVo getFilmDetailById(/*@Param("uuid")*/ String uuid);
}
