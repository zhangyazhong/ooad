package com.github.zhangyazhong.service;


import com.github.zhangyazhong.model.Employee;
import org.springframework.stereotype.Service;

/**
 * @author zhangyazhong
 * @version 1.0
 */
@Service
public interface IEmployeeService {
    Employee login(String phone, String password);
    void logout();
}
