package utils;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.io.IOException;

/**
 * 自定义实现sqlSession 绑定类
 */
public class SqlSessionUtils {
    private SqlSessionFactory sqlSessionFactory;
    private ThreadLocal<SqlSession> sqlSessionThreadLocal;

    public SqlSessionUtils(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public SqlSessionUtils() {
    }

    public SqlSession getSqlSession() throws IOException {
        SqlSession sqlSession;
        sqlSessionThreadLocal = new ThreadLocal<>();
        if (sqlSessionThreadLocal.get() == null) {
            sqlSession = sqlSessionFactory.openSession(false);
            sqlSessionThreadLocal.set(sqlSession);
        } else {
            sqlSession = sqlSessionThreadLocal.get();
        }
        return sqlSession;
    }

    public void destroy() {
        if (sqlSessionThreadLocal != null) {
            sqlSessionThreadLocal.remove();
        }
    }

}
