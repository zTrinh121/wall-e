package com.example.SWP391_Project.config;

import com.example.SWP391_Project.enums.RoleDescription;
import com.example.SWP391_Project.model.Role;
import com.example.SWP391_Project.repository.RoleRepository;
import com.example.SWP391_Project.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class DataInitializer {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void initializeData() {
        // Initialize roles
        Role studentRole = new Role(1, RoleDescription.STUDENT);
        Role parentRole = new Role(2, RoleDescription.PARENT);
        Role teacherRole = new Role(3, RoleDescription.TEACHER);
        Role managerRole = new Role(4, RoleDescription.MANAGER);
        Role adminRole = new Role(5, RoleDescription.ADMIN);

        // Save roles to repository
        roleRepository.saveAll(Arrays.asList(studentRole, parentRole, teacherRole, managerRole, adminRole));

        // Initialize users if needed
        // Example:
        // User adminUser = new User("admin", "password", adminRole);
        // userRepository.save(adminUser);
    }
}
