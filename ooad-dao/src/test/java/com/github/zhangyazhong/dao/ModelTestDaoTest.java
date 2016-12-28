package com.github.zhangyazhong.dao;

import com.github.zhangyazhong.model.ModelTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.sql.Timestamp;

/**
 * @author zhangyazhong
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext_dao.xml"})
public class ModelTestDaoTest {
    @Resource
    private ModelTestDao modelTestDao;
    
    @Test
    public void testSave() throws Exception {
        modelTestDao.save(new ModelTest("SpringTest", new Timestamp(System.currentTimeMillis())));
    }
}