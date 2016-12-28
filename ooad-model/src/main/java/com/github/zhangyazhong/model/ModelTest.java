package com.github.zhangyazhong.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author zhangyazhong
 * @version 1.0
 */
@Entity
@Table(name = "model_test", schema = "ooad")
public class ModelTest {
    private int id;
    private String name;
    private Timestamp date;
    
    public ModelTest() {
    }
    public ModelTest(String name, Timestamp date) {
        this.name = name;
        this.date = date;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Basic
    @Column(name = "date")
    public Timestamp getDate() {
        return date;
    }
    
    public void setDate(Timestamp date) {
        this.date = date;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        ModelTest modelTest = (ModelTest) o;
        
        if (id != modelTest.id) return false;
        if (name != null ? !name.equals(modelTest.name) : modelTest.name != null) return false;
        if (date != null ? !date.equals(modelTest.date) : modelTest.date != null) return false;
        
        return true;
    }
    
    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}
