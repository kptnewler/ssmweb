package factory;

import service.UserService;
import service.impl.UserServiceImpl;

public class InstanceStaticFactory {
    public static UserService getUserService() {
        return new UserServiceImpl();
    }
}
