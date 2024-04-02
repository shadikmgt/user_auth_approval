package com.test.model;

import com.test.model.super_classes.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
@Entity
public class Role extends BaseEntity implements GrantedAuthority {

    @Column(nullable = false)
    private String name;

    public Role() {
    }
    public Role(String name) {
        this.name = name;
        setActive(true);
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
