package dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pojo.Employee;

import java.util.List;

public class EmployeeDaoTest {
    private EmployeeDao employeeDao;
    private ClassPathXmlApplicationContext applicationContext;

    @Before
    public void init() {
        applicationContext = new ClassPathXmlApplicationContext("spring-dao.xml");
        employeeDao = applicationContext.getBean("employeeDao", EmployeeDao.class);
    }
    @After
    public void tearDown() throws Exception {
        if (applicationContext != null) {
            applicationContext.close();
        }
    }

    @Test
    public void getAllEmployees() {
        List<Employee> employees = employeeDao.getAllEmployees();
        employees.forEach(System.out::println);
    }

    @Test
    public void getEmployeesByDepartment() {
        List<Employee> employees = employeeDao.getEmployeesByDepartment(1);
        employees.forEach(System.out::println);
    }
}
