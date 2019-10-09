package dao;

import dao.UserDao;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.QueryVal;
import pojo.User;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class UserDaoTest {
    private InputStream inputStream;
    private SqlSession sqlSession;
    private UserDao userDao;

    @Before
    public void init() throws IOException {
        inputStream = Resources.getResourceAsStream("sqlMapperConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        sqlSession = sqlSessionFactory.openSession();
        userDao = sqlSession.getMapper(UserDao.class);
    }

    @Test
    public void getUser() throws IOException {
        User user = userDao.getUser(1);
       System.out.println(user);
    }

    @Test
    public void saveUser() {
        User user = new User();
        user.setPassword("123");
        user.setName("王老二");

        System.out.println("插入之前：" + user);

        userDao.saveUser(user);

        System.out.println("插入之后：" + user);

        sqlSession.commit();
    }

    @Test
    public void deleteUser() {
        userDao.deleteUser(3);
        sqlSession.commit();
    }

    @Test
    public void updateUser() {
        User user = new User();
        user.setPassword("123");
        user.setName("王老三");
        user.setId(11);
        userDao.updateUser(user);
        sqlSession.commit();
    }

    @Test
    public void getUserByName() {
        List<User> users = userDao.getUserByName("王");
        users.forEach(System.out::println);
    }

    @Test
    public void getUserByQueryUserName() {
        QueryVal queryVal = new QueryVal();
        User user = new User();
        user.setName("%王%");
        queryVal.setUser(user);
        List<User> users = userDao.getUserByQueryUserName(queryVal);
        users.forEach(System.out::println);
    }

    @After
    public void destroy() throws IOException {
        sqlSession.close();
        inputStream.close();
    }
}
