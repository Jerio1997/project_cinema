package com.stylefeng.guns.rest.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.api.user.UserService;
import com.stylefeng.guns.api.user.vo.User;
import com.stylefeng.guns.api.user.vo.UserInfo;
import com.stylefeng.guns.core.util.MD5Util;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeUserTMapper;
import com.stylefeng.guns.rest.common.persistence.model.MtimeUserT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Service(interfaceClass = UserService.class, loadbalance = "roundrobin")
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

    @Override
    public boolean checkUsername(String username) {
        MtimeUserT userT = new MtimeUserT();
        userT.setUserName(username);
        MtimeUserT mtimeUserT = userTMapper.selectOne(userT);
        return mtimeUserT != null;
    }

    @Override
    public boolean updateUserInfo(UserInfo userInfo) {
        MtimeUserT userT = new MtimeUserT();
        userT.setUuid(userInfo.getUuid());
        userT.setNickName(userInfo.getNickname());
        userT.setUserSex(userInfo.getSex());
        userT.setBirthday(userInfo.getBirthday());
        userT.setEmail(userInfo.getEmail());
        userT.setUserPhone(userInfo.getPhone());
        userT.setAddress(userInfo.getAddress());
        userT.setBiography(userInfo.getBiography());
        userT.setLifeState(Integer.parseInt(userInfo.getLifeState()));
        userT.setUpdateTime(new Date());
        Integer integer = userTMapper.updateById(userT);
        return integer != 0;
    }

    @Override
    public UserInfo queryUserById(Integer uuid) {
        MtimeUserT mtimeUserT = userTMapper.selectById(uuid);
        UserInfo userInfo = new UserInfo();
        userInfo.setUuid(uuid);
        userInfo.setNickname(mtimeUserT.getNickName());
        // TODO
        return userInfo;
    }

    @Override
    public User getUserByUsername(String userName) {
        User user = new User();
        user.setUsername(userName);
        Map<String,Object> map = new HashMap<>();
        map.put("user_name",userName);
        List<MtimeUserT> mtimeUserTList = userTMapper.selectByMap(map);
        if (mtimeUserTList.size() == 1) {
            String password = MD5Util.encrypt(mtimeUserTList.get(0).getUserPwd());
            user.setPassword(password);
            return user;
        }
        return null;
    }
}
