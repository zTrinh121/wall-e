package com.example.config;

import com.example.model.Role;
import com.example.model.User;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.mindrot.jbcrypt.BCrypt;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        // Thêm các vai trò nếu chưa có
        if (roleRepository.count() == 0) {
            roleRepository.save(new Role(1, Role.RoleDescription.STUDENT));
            roleRepository.save(new Role(2, Role.RoleDescription.PARENT));
            roleRepository.save(new Role(3, Role.RoleDescription.TEACHER));
            roleRepository.save(new Role(4, Role.RoleDescription.MANAGER));
            roleRepository.save(new Role(5, Role.RoleDescription.ADMIN));
        }

        // Thêm người dùng mẫu nếu chưa có
        if (userRepository.count() == 0) {
            Role adminRole = roleRepository.findById(5).orElse(null);
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(BCrypt.hashpw("password", BCrypt.gensalt()));
            admin.setEmail("admin@example.com");
            admin.setRole(adminRole);
            userRepository.save(admin);
        }
    }
}
