package com.github.zhangyazhong.service.impl;

import com.github.zhangyazhong.common.hql.query.HqlQuerySafe;
import com.github.zhangyazhong.common.hql.query.HqlQueryStatement;
import com.github.zhangyazhong.common.session.Session;
import com.github.zhangyazhong.dao.EntityDao;
import com.github.zhangyazhong.model.Employee;
import com.github.zhangyazhong.service.IEmployeeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zhangyazhong
 * @version 1.0
 */
@Service
public class EmployeeServiceImpl implements IEmployeeService {
    @Resource
    private EntityDao<Employee> employeeDao;
    
    @Override
    public Employee login(String phone, String password) {
        HqlQueryStatement findEmployee = new HqlQuerySafe("Employee")
                .where("phone=" + phone)
                .where("password=" + password);
        Employee employee = employeeDao.find(findEmployee);
        if (employee != null) {
            Session.setAttribute("employee", employee);
        }
        return employee;
    }
    
    @Override
    public void logout() {
        Session.removeAttribute("employee");
    }
}
