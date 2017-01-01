package com.github.zhangyazhong.model;

import javax.persistence.*;

/**
 * @author zhangyazhong
 * @version 1.0
 */
@Entity
@Table(name = "action", schema = "ooad")
public class Action {
    private int id;
    private String description;
    
    public final static String BUY = "购入";
    public final static String RECEIVE = "领用";
    public final static String RETURN = "归还";
    public final static String DISCARD = "报废";
    public final static String INSTALL = "安装";
    
    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Action action = (Action) o;
        
        if (id != action.id) return false;
        if (description != null ? !description.equals(action.description) : action.description != null) return false;
        
        return true;
    }
    
    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
