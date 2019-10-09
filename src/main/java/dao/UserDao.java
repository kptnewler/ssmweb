package dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import pojo.QueryVal;
import pojo.User;

import java.util.List;


public interface UserDao {
    User getUser(long id);

    long saveUser(User user);

    void deleteUser(long id);

    void updateUser(User user);

    List<User> getUserByName(String name);

    List<User> getUserByQueryUserName(QueryVal queryVal);
}
