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
// 切点和通知组成了切面
public class TransactionManager {

//    @Autowired
    private SqlSession sqlSession;

    // 切点定义了何处切入，作用的方法表示切入的逻辑
    @Pointcut("execution(* service.UserService.*(..))")
    private void pct() {

    }

//    public TransactionManager(SqlSession sqlSession) {
//        this.sqlSession = sqlSession;
//    }

    // 通知定义了何时切入，与切点共同使用作用在连接点上（commit方法）
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
