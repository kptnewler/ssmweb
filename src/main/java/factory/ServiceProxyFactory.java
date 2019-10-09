package factory;


import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import service.UserService;
import service.impl.UserServiceImpl;
import utils.TransactionManager;

import java.lang.reflect.Method;

public class ServiceProxyFactory {
    private TransactionManager transactionManager;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    private UserService userService;
    public ServiceProxyFactory(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public UserService getUserService() {
        return (UserService) Enhancer.create(UserService.class, new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                Object proxyUserService = null;
                try {
                    proxyUserService = method.invoke(userService, objects);
                    transactionManager.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                    transactionManager.rollback();
                }

                return proxyUserService;
            }
        });
    }
}
