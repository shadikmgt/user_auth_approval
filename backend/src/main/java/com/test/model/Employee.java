package com.test.model;


import com.test.model.super_classes.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee extends BaseEntity {

    private String employeeName;

    private String address;

    private String email;

    private Integer age;

    private Float salary;
    
    private boolean approved;


}
