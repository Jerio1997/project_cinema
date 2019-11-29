package com.stylefeng.guns.rest.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.api.user.UserService;
import com.stylefeng.guns.api.user.vo.User;
import com.stylefeng.guns.core.util.MD5Util;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeUserTMapper;
import com.stylefeng.guns.rest.common.persistence.model.MtimeUserT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private MtimeUserTMapper userTMapper;

    @Override
    public boolean register(User user) {
        // 注册信息转换为数据实体
        MtimeUserT userT = new MtimeUserT();
        userT.setUserName(user.getUsername());
        userT.setEmail(user.getEmail());
        userT.setUserPhone(user.getPhone());
        userT.setAddress(user.getAddress());
        userT.setBeginTime(new Date());
        userT.setUpdateTime(new Date());

        // 给密码加密
        String password = MD5Util.encrypt(user.getPassword());
        userT.setUserPwd(password);

        Integer insert = userTMapper.insert(userT);
        return insert != 0;
    }
}
