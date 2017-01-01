package com.github.zhangyazhong.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author zhangyazhong
 * @version 1.0
 */
@Entity
@Table(name = "spare_record", schema = "ooad")
public class SpareRecord {
    private int id;
    private Timestamp date;
    private Employee employee;
    private Spare spare;
    private Assets assets;
    private Action action;
    
    public SpareRecord() {
    }
    public SpareRecord(Timestamp date, Employee employee, Spare spare, Assets assets, Action action) {
        this.date = date;
        this.employee = employee;
        this.spare = spare;
        this.assets = assets;
        this.action = action;
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
        
        SpareRecord that = (SpareRecord) o;
        
        if (id != that.id) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        
        return true;
    }
    
    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employeeId", referencedColumnName = "id")
    public Employee getEmployee() {
        return employee;
    }
    
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "spareId", referencedColumnName = "id")
    public Spare getSpare() {
        return spare;
    }
    
    public void setSpare(Spare spareBySpareId) {
        this.spare = spareBySpareId;
    }
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "assetsId", referencedColumnName = "id")
    public Assets getAssets() {
        return assets;
    }
    
    public void setAssets(Assets assets) {
        this.assets = assets;
    }
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "actionId", referencedColumnName = "id")
    public Action getAction() {
        return action;
    }
    
    public void setAction(Action action) {
        this.action = action;
    }
    
    @Transient
    public boolean isIdle() {
        return this.getAction().getDescription().equals(Action.BUY)
                || this.getAction().getDescription().equals(Action.RETURN);
    }
    @Transient
    public boolean isUsing() {
        return this.getAction().getDescription().equals(Action.INSTALL);
    }
}
