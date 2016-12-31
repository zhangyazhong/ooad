package com.github.zhangyazhong.model;

import javax.persistence.*;
import java.util.Set;

/**
 * @author zhangyazhong
 * @version 1.0
 */
@Entity
@Table(name = "role", schema = "ooad")
public class Role {
    private int id;
    private String description;
    private Set<Action> actionSet;
    
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
    @Column(name = "description")
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_action",
            joinColumns = {@JoinColumn(name = "roleId", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name="actionId", referencedColumnName = "id")})
    public Set<Action> getActionSet() {
        return actionSet;
    }
    
    public void setActionSet(Set<Action> actionSet) {
        this.actionSet = actionSet;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Role role = (Role) o;
        
        if (id != role.id) return false;
        if (description != null ? !description.equals(role.description) : role.description != null) return false;
        
        return true;
    }
    
    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
