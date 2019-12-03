package com.stylefeng.guns.order.common.persistence.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.stylefeng.guns.order.common.persistence.model.MoocOrderT;

/**
 * <p>
 * 订单信息表 Mapper 接口
 * </p>
 *
 * @author xxxing
 * @since 2019-12-02
 */
public interface MoocOrderTMapper extends BaseMapper<MoocOrderT> {

    int getLastId();

}
