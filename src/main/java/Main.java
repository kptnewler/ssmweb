import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.UserService;
import service.impl.UserServiceImpl;

public class Main {
    public static UserService userService;
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-dao.xml");
        userService = applicationContext.getBean("userService", UserServiceImpl.class);
        System.out.println(userService.getUserByName("王"));
    }
}
