package service.impl;

import dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pojo.User;
import service.UserService;

import java.util.List;

@Service("userService")
@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, readOnly = false)
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public void init() {
        System.out.println("UserService初始化");
    }

    @Override
    public List<User> getUserByName(String username) {
        return userDao.getUserByName(username);
    }

    @Override
    public User updateUserNameById(long id, String username) {
        User user = userDao.getUser(id);
        if (user != null) {
            user.setName(username);
        }
        userDao.updateUser(user);
        int a = 1/0;
        return user;
    }

    @Override
    public void destroy() {
        System.out.println("UserService销毁");
    }
}
