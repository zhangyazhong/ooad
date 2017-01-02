package com.github.zhangyazhong.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangyazhong
 * @version 1.0
 */
@Entity
@Table(name = "spare", schema = "ooad")
public class Spare {
    private int id;
    private String name;
    private List<SpareRecord> spareRecordList;
    private SpareRecord status;
    
    public Spare() {
    }
    public Spare(String name) {
        this.name = name;
    }
    public Spare(int id, String name) {
        this.id = id;
        this.name = name;
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
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Spare spare = (Spare) o;
        
        if (id != spare.id) return false;
        if (name != null ? !name.equals(spare.name) : spare.name != null) return false;
        
        return true;
    }
    
    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
    
    @OneToMany(mappedBy = "spare", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OrderBy("id asc")
    public List<SpareRecord> getSpareRecordList() {
        return spareRecordList;
    }
    
    public void setSpareRecordList(List<SpareRecord> spareRecordList) {
        this.spareRecordList = spareRecordList;
        if (spareRecordList != null && !spareRecordList.isEmpty()) {
            this.status = spareRecordList.get(spareRecordList.size() - 1);
        }
    }
    
    @Transient
    public SpareRecord getStatus() {
        return status;
    }
    
    public void addSpareRecord(SpareRecord spareRecord) {
        if (this.getSpareRecordList() == null) {
            this.setSpareRecordList(new ArrayList<>());
        }
        this.getSpareRecordList().add(spareRecord);
        this.status = spareRecord;
    }
}
