package com.test;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.test.dao.UserRepository;
import com.test.model.Role;
import com.test.model.User;

import lombok.Getter;

@SpringBootApplication
public class EmployeeApplication {
    @Getter
    private static ApplicationContext context;

    public static void main(String[] args) {
        context = SpringApplication.run(EmployeeApplication.class, args);
        UserRepository repo = context.getBean(UserRepository.class);
        if (repo.count() < 1) {
            User user1 = new User();
            user1.setName("A");
            user1.setUsername("employee1@gmail.com");
            user1.setPassword(new BCryptPasswordEncoder().encode("1234"));
            user1.setActive(true);
            user1.setRoles(Arrays.asList(new Role("CHECKER")));
            repo.save(user1);

            User user2 = new User();
            user2.setName("B");
            user2.setUsername("employee2@gmail.com");
            user2.setPassword(new BCryptPasswordEncoder().encode("1234"));
            user2.setActive(true);
            user2.setRoles(Arrays.asList(new Role("CHECKER")));
            repo.save(user2);
        }
    }
}
