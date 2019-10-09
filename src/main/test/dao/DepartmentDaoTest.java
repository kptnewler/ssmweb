package dao;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.Department;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class DepartmentDaoTest {
    private InputStream inputStream;
    private SqlSession sqlSession;
    private DepartmentDao departmentDao;

    @Before
    public void init() throws IOException {
        inputStream = Resources.getResourceAsStream("sqlMapperConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        sqlSession = sqlSessionFactory.openSession();
        departmentDao = sqlSession.getMapper(DepartmentDao.class);
    }
    @After
    public void tearDown() throws Exception {
        inputStream.close();
        sqlSession.close();
    }

    @Test
    public void getAllDepartments() {
        List<Department> departments = departmentDao.getAllDepartments();
        for (Department department : departments) {
            System.out.println(department.getLocation());
        }
    }
}
