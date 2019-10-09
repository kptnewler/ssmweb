package service;

import pojo.User;

import java.util.List;

public interface UserService {
    void init();
    List<User> getUserByName(String username);
    User updateUserNameById(long userId, String username);
    void destroy();
}
