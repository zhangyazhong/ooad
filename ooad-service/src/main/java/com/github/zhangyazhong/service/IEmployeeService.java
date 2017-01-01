package com.github.zhangyazhong.service;


import com.github.zhangyazhong.model.Employee;
import org.springframework.stereotype.Service;

/**
 * @author zhangyazhong
 * @version 1.0
 */
@Service
public interface IEmployeeService {
    /**
     * IT资产管理系统登录入口
     *
     * @param phone 手机号
     * @param password 密码
     * @return 用户实体
     */
    Employee login(String phone, String password);
    
    /**
     * IT资产管理系统注销入口
     */
    void logout();
}
