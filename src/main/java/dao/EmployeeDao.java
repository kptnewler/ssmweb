package dao;

import pojo.Department;
import pojo.Employee;

import java.util.List;

public interface EmployeeDao {
    List<Employee> getAllEmployees();

    List<Employee> getEmployeesByDepartment(long depId);
}
