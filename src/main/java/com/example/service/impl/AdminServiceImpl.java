package com.example.service.impl;

import com.example.repository.UserRepository;
import com.example.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public long getUserCount() {
        return userRepository.count();
    }

    @Override
    public long getManagement() {
        return userRepository.countByRole_Id(4);
    }
}
