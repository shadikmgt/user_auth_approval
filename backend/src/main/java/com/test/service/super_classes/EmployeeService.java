package com.test.service.super_classes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.test.dto.Response;
import com.test.model.Employee;

public interface EmployeeService extends CrudService<Employee, Long> {

    Response<String> storeData(Employee data);

    Response<Page<Employee>> getAll(Pageable pageable);

    Response<Employee> getById(Long id);

    Response<String> delete(Long id);

    Response<String> approve(Long id);

    String validate(Employee data);

    String checkDuplicate(Employee data);


}
