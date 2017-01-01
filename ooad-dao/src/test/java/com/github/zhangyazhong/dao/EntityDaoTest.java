package com.github.zhangyazhong.dao;

import com.github.zhangyazhong.common.hql.query.HqlQuerySafe;
import com.github.zhangyazhong.common.hql.query.HqlQueryStatement;
import com.github.zhangyazhong.model.Employee;
import com.github.zhangyazhong.model.Role;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhangyazhong
 * @version 1.0
 */
public class EntityDaoTest extends BaseTest {
    @Resource
    private EntityDao<Role> roleDao;
    @Resource
    private EntityDao<Employee> employeeDao;
    
    /**
     * 查找权限`id`为`1`的角色role，正确返回为管理员
     */
    @Test
    public void testFind() {
        Role role = roleDao.find(new HqlQuerySafe("Role").where("id=1"));
        assert role != null;
        assert role.getId() == 1;
        assert role.getDescription().equals(Role.ADMIN);
    }
    
    /**
     * 查询权限`id`为`1`的角色role，正确返回为管理员
     */
    @Test
    public void testQuery() {
        HqlQueryStatement hqlQueryStatement;
        List<Role> list;
        hqlQueryStatement = new HqlQuerySafe("Role").where("id=1");
        list = roleDao.query(hqlQueryStatement);
        assert list.size() == 1;
        assert list.get(0).getId() == 1;
        assert list.get(0).getDescription().equals(Role.ADMIN);
        hqlQueryStatement = new HqlQuerySafe("Role").orderBy(HqlQueryStatement.Order.ASC, "id");
        list = roleDao.query(hqlQueryStatement, 0, 1);
        assert list.size() == 1;
        assert list.get(0).getId() == 1;
        assert list.get(0).getDescription().equals(Role.ADMIN);
        String sql = "SELECT * FROM `role` WHERE id='1'";
        list = roleDao.query(sql, Role.class, 0, 1);
        assert list.size() == 1;
        assert list.get(0).getId() == 1;
        assert list.get(0).getDescription().equals(Role.ADMIN);
    }
    
    /**
     * 存储新的role至数据库，并测试验证存储过程的正确性
     */
    @Test
    public void testSave() {
        String description = "JUnit测试" + RandomStringUtils.randomNumeric(6);
        Role role = new Role(description);
        roleDao.save(role);
        HqlQueryStatement hqlQueryStatement = new HqlQuerySafe("Role")
                .where(ImmutableMap.of("description", description));
        List<Role> list = roleDao.query(hqlQueryStatement);
        assert list.size() == 1;
        assert list.get(0).getDescription().equals(description);
    }
    
    /**
     * 更新某个现有的role信息，并测试验证更新过程的正确性
     */
    @Test
    public void testUpdate() {
        String description = "JUnit测试" + RandomStringUtils.randomNumeric(6);
        Role role1 = roleDao.query(new HqlQuerySafe("Role").orderBy(HqlQueryStatement.Order.DESC, "id")).get(0);
        role1.setDescription(description);
        roleDao.update(role1);
        Role role2 = roleDao.query(new HqlQuerySafe("Role").where("id=" + role1.getId() + "")).get(0);
        assert role2.getDescription().equals(description);
    }
    
    @Test
    public void testFetch() {
        Employee employee = employeeDao.find(new HqlQuerySafe("Employee").where("id=1"));
        System.out.println(employee.getRole().getDescription());
        assert employee.getRole() != null;
    }
}