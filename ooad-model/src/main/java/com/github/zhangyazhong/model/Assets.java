package com.github.zhangyazhong.model;

import javax.persistence.*;
import java.util.Set;

/**
 * @author zhangyazhong
 * @version 1.0
 */
@Entity
@Table(name = "assets", schema = "ooad")
public class Assets {
    private int id;
    private String name;
    private String type;
    private Set<AssetsRecord> assetsRecordSet;
    
    @Id
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
    @Column(name = "type")
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    @OneToMany(mappedBy = "assets")
    public Set<AssetsRecord> getAssetsRecordSet() {
        return assetsRecordSet;
    }
    
    public void setAssetsRecordSet(Set<AssetsRecord> assetsRecordSet) {
        this.assetsRecordSet = assetsRecordSet;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Assets assets = (Assets) o;
        
        if (id != assets.id) return false;
        if (name != null ? !name.equals(assets.name) : assets.name != null) return false;
        if (type != null ? !type.equals(assets.type) : assets.type != null) return false;
        
        return true;
    }
    
    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
    
    @Override
    public String toString() {
        return id + " | " + name + " | " + type;
    }
}
