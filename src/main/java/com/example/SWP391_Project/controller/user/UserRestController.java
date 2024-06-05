package com.example.SWP391_Project.controller.user;

import com.example.SWP391_Project.model.User;
import com.example.SWP391_Project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers(@RequestParam(required = false) Integer role) {
        if (role != null) {
            return userService.findUsersByRole(role);
        } else {
            return userService.getAllUsers();
        }

    }

    @GetMapping("/countByRole")
    @ResponseBody
    public long countUsersByRole(@RequestParam int roleId) {
        return userService.countByRole_Id(roleId);
    }
}
