package com.example.SWP391_Project.controller.user;

import com.example.SWP391_Project.model.User;
import com.example.SWP391_Project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAllUsers(@RequestParam(required = false) Integer role) {
        List<Map<String, Object>> users;
        if (role != null) {
            users = userService.getUsersByRoleId(role);
        } else {
            users = userService.getAllUsersWithSpecificAttributes();
        }
        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/countByRole")
    @ResponseBody
    public long countUsersByRole(@RequestParam int roleId) {
        return userService.countByRole_Id(roleId);
    }
}
