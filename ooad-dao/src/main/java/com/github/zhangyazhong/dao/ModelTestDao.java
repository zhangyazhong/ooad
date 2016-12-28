package com.github.zhangyazhong.dao;

import com.github.zhangyazhong.model.ModelTest;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author zhangyazhong
 * @version 1.0
 */
@Transactional
@Component
public class ModelTestDao {
    
    @Resource
    private SessionFactory sessionFactory;
    
    public void save(ModelTest modelTest) {
        this.getSession().save(modelTest);
    }
    
    public Session getSession() {
        return this.getSessionFactory().getCurrentSession();
    }
    
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
