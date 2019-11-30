package com.stylefeng.guns.api.user;

import com.stylefeng.guns.api.user.vo.User;
import com.stylefeng.guns.api.user.vo.UserInfo;

public interface UserService {

    boolean register(User user);

    boolean checkUsername(String username);

    boolean updateUserInfo(UserInfo userInfo);

    UserInfo queryUserById(Integer uuid);

    UserInfo getUserByUsername(String userName);
}
