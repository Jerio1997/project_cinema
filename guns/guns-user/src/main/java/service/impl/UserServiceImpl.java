package service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.api.user.UserService;
import com.stylefeng.guns.api.user.vo.User;
import org.springframework.stereotype.Component;

@Component
@Service
public class UserServiceImpl implements UserService {

    @Override
    public User selectUserByUsernamePassword(User user) {
        return null;
    }

    @Override
    public boolean insertUserByUsernamePassword(User user) {
        return false;
    }
}
