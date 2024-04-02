package com.test.dao;

import com.test.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, CrudDao<Employee, Long> {


    @Modifying
    @Query(value = "UPDATE Employee e " +
            "SET e.active = false " +
            "where e.id = :id")
    int softDeleteById(@Param("id") Long id);

    List<Employee> findByActive(boolean active);

}