package com.github.zhangyazhong.service;

import com.github.zhangyazhong.common.session.Session;
import com.github.zhangyazhong.model.Employee;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author zhangyazhong
 * @version 1.0
 */
public class EmployeeServiceTest extends BaseTest {
    @Resource
    private IEmployeeService employeeService;
    
    @BeforeClass
    public static void beforeClass() {
        Session.clear();
    }
    
    @Test
    public void testLogin() {
        Employee employee = employeeService.login("13061991332", "123456");
        assert employee != null;
        assert employee.getName().equals("张亚中");
        assert employee.getRole().getDescription().equals("管理员");
    }
    
    @Test
    public void testLogout() {
        Employee employee = employeeService.login("13061991332", "123456");
        assert Session.getAttribute("employee") != null;
        assert employee.equals(Session.getAttribute("employee"));
        employeeService.logout();
        assert Session.getAttribute("employee") == null;
    }
}
