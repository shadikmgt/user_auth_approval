package com.test.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.test.controller.super_classes.CrudController;
import com.test.dto.Response;
import com.test.model.Employee;
import com.test.service.super_classes.EmployeeService;
import com.test.utils.PageUtil;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("employee/")
public class EmployeeController implements CrudController<Employee, Long> {
    private final EmployeeService employeeService;

    @Override
    public ResponseEntity<Response> storeData(Employee data) {
        Response response = employeeService.storeData(data);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Response<Page<Employee>>> getAll(Integer pageNumber, Integer pageSize, String sortDirection,
            String sortColumns) {
        Pageable pageable = PageUtil.getPageable(pageNumber, pageSize, sortDirection, sortColumns);
        Response<Page<Employee>> response = employeeService.getAll(pageable);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Response<Employee>> getOne(Long id) {
        Response<Employee> response = employeeService.getById(id);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Response> delete(Long id) {
        Response response = employeeService.delete(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "approve", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> approve(@RequestBody() Map<String, Long> data) {
        Response response = employeeService.approve(data.get("id"));
        return ResponseEntity.ok(response);
    }
}
