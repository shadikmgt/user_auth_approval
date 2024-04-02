package com.test.service;

import static com.test.constants.enums.OperationStatus.FAILURE;
import static com.test.constants.enums.OperationStatus.SUCCESS;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.test.dao.EmployeeRepository;
import com.test.dto.MailInfo;
import com.test.dto.Response;
import com.test.model.Employee;
import com.test.service.mail.MailService;
import com.test.service.super_classes.EmployeeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;
    private final MailService mailService;

    @Override
    public Response<String> storeData(Employee data) {
        String validationMsg = validate(data);
        if (validationMsg == null) {
            repository.save(data);
            return new Response<String>(SUCCESS, "Successfully stored data", null);
        } else {
            return new Response<String>(FAILURE, validationMsg, null);
        }
    }

    @Override
    public Response<Page<Employee>> getAll(Pageable pageable) {
        Page<Employee> page = repository.findByActive(true, pageable);
        return new Response<>(SUCCESS, null, page);
    }

    @Override
    public Response<Employee> getById(Long id) {
        Employee employee = repository.findById(id).orElse(new Employee());
        return new Response<>(SUCCESS, null, employee);
    }

    @Override
    public Response<String> delete(Long id) {
        repository.softDeleteById(id);
        return new Response<String>(SUCCESS, "Deleted successfully", null);
    }

    @Override
    public Response<String> approve(Long id) {
        Employee employee = repository.findById(id).orElse(new Employee());
        if (employee.hasId()) {
            employee.setApproved(true);
            repository.save(employee);
            new Thread(() -> {
                try {
                    MailInfo mailInfo = new MailInfo();
                    mailInfo.setSubject("Employee Approved");
                    mailInfo.setBody("Your account has been approved");
                    mailInfo.setSendTo(new String[]{employee.getEmail()});
                    mailInfo.setHtml(false);                    
                    mailService.sendEmail(mailInfo);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }).start();
            return new Response<String>(SUCCESS, "Approved successfully", null);
        }
        return new Response<String>(FAILURE, "Could not find employee", null);
    }

    @Override
    public String validate(Employee data) {
        return checkDuplicate(data);
    }

    @Override
    public String checkDuplicate(Employee data) {
        return null;
    }

}
