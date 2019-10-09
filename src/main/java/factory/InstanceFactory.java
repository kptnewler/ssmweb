package factory;

import service.UserService;
import service.impl.UserServiceImpl;

public class InstanceFactory {
    public UserService getUserService() {
        return new UserServiceImpl();
    }
}
