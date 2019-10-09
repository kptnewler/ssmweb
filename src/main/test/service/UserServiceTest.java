package service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pojo.User;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-dao.xml")
public class UserServiceTest {
    @Autowired
    @Qualifier("userService")
    private UserService userService;
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() {
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("spring-dao.xml");
        AutowireCapableBeanFactory factory = applicationContext.getAutowireCapableBeanFactory();
        UserService userService = factory.getBean("userService", UserService.class);
        System.out.println(userService);

        User user = factory.getBean("user", User.class);
        System.out.println(user);
    }

    @Test
    public void getUserByName() {
       List<User> users =  userService.getUserByName("王");
       System.out.println(users);
    }

    @Test
    public void updateUserNameById() {
        User user = userService.updateUserNameById(1, "李白");
        System.out.println(user);
    }
}
