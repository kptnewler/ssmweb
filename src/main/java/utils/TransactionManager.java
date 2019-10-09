package utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

/**
 * 自定义实现事务管理类
 */
@Component("transactionManager")
@Aspect
public class TransactionManager {

//    @Autowired
    private SqlSession sqlSession;

    @Pointcut("execution(* service.UserService.*(..))")
    private void pct() {

    }

//    public TransactionManager(SqlSession sqlSession) {
//        this.sqlSession = sqlSession;
//    }

    @AfterReturning(pointcut = "pct()")
    public void commit() {
        System.out.println("提交事务");
        sqlSession.commit();
    }

    @AfterThrowing(pointcut = "pct()")
    public void rollback() {
        System.out.println("回滚");
        sqlSession.rollback();
    }

    @After("pct()")
    public void close() {
        System.out.println("最终关闭连接");
    }

}
