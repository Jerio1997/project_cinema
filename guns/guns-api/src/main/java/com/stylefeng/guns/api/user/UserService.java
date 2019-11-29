package com.stylefeng.guns.api.user;

import com.stylefeng.guns.api.user.vo.User;

public interface UserService {
    User selectUserByUsernamePassword(User user);

    boolean insertUserByUsernamePassword(User user);
}
