package com.github.zhangyazhong.dao;

import com.github.zhangyazhong.model.ModelTest;
import junit.framework.TestCase;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.Timestamp;

/**
 * @author zhangyazhong
 * @version 1.0
 */
public class ModelTestDaoTest extends TestCase {
    public void testSave() throws Exception {
        ApplicationContext factory=new ClassPathXmlApplicationContext("classpath:applicationContext_dao.xml");
        ModelTestDao modelTestDao = factory.getBean(ModelTestDao.class);
        modelTestDao.save(new ModelTest("clean", new Timestamp(System.currentTimeMillis())));
    }
}