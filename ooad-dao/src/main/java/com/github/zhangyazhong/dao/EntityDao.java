package com.github.zhangyazhong.dao;

import com.github.zhangyazhong.common.hql.query.HqlQueryStatement;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhangyazhong
 * @version 1.0
 */
@Transactional
@Component
@SuppressWarnings("unchecked")
public class EntityDao<T> {
    @Resource
    private SessionFactory sessionFactory;
    
    public T find(HqlQueryStatement hqlQueryStatement) {
        List<T> list = query(hqlQueryStatement, 0, 1);
        return !list.isEmpty() ? list.get(0) : null;
    }
    
    public List<T> query(HqlQueryStatement hqlQueryStatement) {
        try {
            Query query = this.getSession().createQuery(hqlQueryStatement.createStatement());
            hqlQueryStatement.getQueryParameters().forEach(query::setString);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<T> query(HqlQueryStatement hqlQueryStatement, int start, int limit) {
        try {
            Query query = this.getSession().createQuery(hqlQueryStatement.createStatement());
            hqlQueryStatement.getQueryParameters().forEach(query::setString);
            query.setFirstResult(start);
            query.setMaxResults(limit);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<T> query(String sql, Class clazz) {
        SQLQuery query = this.getSession().createSQLQuery(sql);
        query.addEntity(clazz);
        return query.list();
    }
    public List<T> query(String sql, Class clazz, int start, int limit) {
        SQLQuery query = this.getSession().createSQLQuery(sql);
        query.addEntity(clazz);
        query.setFirstResult(start);
        query.setMaxResults(limit);
        return query.list();
    }
    
    public void save(T t) {
        this.getSession().save(t);
    }
    
    public void update(T t) {
        this.getSession().saveOrUpdate(t);
    }
    
    public Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }
}
