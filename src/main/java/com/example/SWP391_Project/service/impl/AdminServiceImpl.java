package com.example.SWP391_Project.service.impl;

import com.example.SWP391_Project.dto.SystemNotificationDto;
import com.example.SWP391_Project.enums.RoleDescription;
import com.example.SWP391_Project.enums.Status;
import com.example.SWP391_Project.model.*;
import com.example.SWP391_Project.dto.PrivateNotificationDto;
import com.example.SWP391_Project.repository.*;
import com.example.SWP391_Project.response.CenterDetailResponse;
import com.example.SWP391_Project.response.CenterPostResponse;
import com.example.SWP391_Project.service.AdminService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SystemNotificationRepository systemNotificationRepository;

    @Autowired
    PrivateNotificationRepository privateNotificationRepository;

    @Autowired
    private CenterPostRepository centerPostRepository;

    @Autowired
    private CenterRepository centerRepository;


    @Override
    public long getUserCount() {
        return userRepository.count();
    }

    @Override
    public long getManagement() {
        return userRepository.countByRole_Id(4);
    }

    @Override
    public List<SystemNotification> getAllSystemNotifications() {
        return systemNotificationRepository
                .findAll(Sort.by(Sort.Direction.DESC, "id"));
    }


    @Override
    public SystemNotification createSystemNotification(SystemNotificationDto systemNotificationDto) {
        SystemNotification system = SystemNotification.builder()
                .title(systemNotificationDto.getTitle())
                .content(systemNotificationDto.getContent())
                .createdAt(new Date())
                .build();

        return systemNotificationRepository.save(system);
    }

    public SystemNotification findById(int id) {
        return systemNotificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("The system notification hasn't been existed ! "));
    }

    @Override
    public SystemNotification updateSystemNotification(int id, SystemNotificationDto systemNotificationDto) {
        SystemNotification system = findById(id);

        system.setTitle(systemNotificationDto.getTitle());
        system.setContent(system.getContent());
        system.setUpdatedAt(new Date());

        return systemNotificationRepository.save(system);
    }

    @Override
    public boolean deleteSystemNotification(int id) {
        try {
            systemNotificationRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<CenterPostResponse> getCenterPosts() {
        return centerPostRepository.findAllCenterPostsAndManagerDetails();
    }




    @Override
    public void approveCenterPost(int id) {
        try {
            CenterPost centerPost = centerPostRepository.findById(id)
                    .orElseThrow(() -> new ServiceException("Center post found with ID: " + id));
            centerPost.setStatus(Status.Approved);
            centerPostRepository.save(centerPost);
        } catch (DataAccessException e) {
            throw new ServiceException("Failed to approve manager center post with ID: " + id, e);
        }
    }

    @Override
    public void rejectCenterPost(int id) {
        try {
            CenterPost centerPost = centerPostRepository.findById(id)
                    .orElseThrow(() -> new ServiceException("Center post not found with ID: " + id));
            centerPost.setStatus(Status.Rejected);
            centerPostRepository.save(centerPost);
        } catch (DataAccessException e) {
            throw new ServiceException("Failed to reject manager center post with ID: " + id, e);
        }
    }

    @Override
    public List<CenterDetailResponse> getCenters() {
        return centerRepository.findAllCentersWithManagerDetails();
    }

    @Override
    public void approveCenterApply(int id) {
        try {
            Center center = centerRepository.findById(id)
                    .orElseThrow(() -> new ServiceException("Center not found with ID: " + id));
            center.setStatus(Status.Approved);
            centerRepository.save(center);
        } catch (DataAccessException e) {
            throw new ServiceException("Failed to approve center with ID: " + id, e);
        }
    }

    @Override
    public void rejectCenterApply(int id) {
        try {
            Center center = centerRepository.findById(id)
                    .orElseThrow(() -> new ServiceException("Center not found with ID: " + id));
            center.setStatus(Status.Rejected);
            centerRepository.save(center);
        } catch (DataAccessException e) {
            throw new ServiceException("Failed to reject center with ID: " + id, e);
        }
    }

    // ----------------------- Private notification ----------------------------
    @Override
    public List<PrivateNotification> getAllPrivateNotifications() {
        return privateNotificationRepository
                .findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Override
    public PrivateNotification createPrivateNotification(PrivateNotificationDto privateNotificationDto) {
        String sendTo = privateNotificationDto.getSendTo();
        if (sendTo.startsWith("USER")) {
            Optional<User> userCode = userRepository.findByCode(privateNotificationDto.getSendTo());
            if (userCode.isPresent()) {
                PrivateNotification privateNotification = PrivateNotification.builder()
                        .title(privateNotificationDto.getTitle())
                        .content(privateNotificationDto.getContent())
                        .createdAt(new Date())
                      //  .actor(Actor.ADMIN)
                        .actor(RoleDescription.ADMIN)
                        .userSendTo(userCode.get())
                        .centerSendTo(null)
                        .build();
                return privateNotificationRepository.save(privateNotification);
            } else {
                throw new IllegalArgumentException("User not found");
            }
        } else if (sendTo.startsWith("CENTER")) {
            Optional<Center> centerOptional = centerRepository.findByCode(privateNotificationDto.getSendTo());
            if (centerOptional.isPresent()) {
                PrivateNotification privateNotification = PrivateNotification.builder()
                        .title(privateNotificationDto.getTitle())
                        .content(privateNotificationDto.getContent())
                        .createdAt(new Date())
                       // .actor(Actor.ADMIN)
                        .actor(RoleDescription.ADMIN)
                        .userSendTo(null)
                        .centerSendTo(centerOptional.get())
                        .build();
                return privateNotificationRepository.save(privateNotification);
            } else {
                throw new IllegalArgumentException("Center not found");
            }
        } else {
            throw new IllegalArgumentException("Invalid sendTo value");
        }
    }

    @Override
    public PrivateNotification updatePrivateNotification(int id, PrivateNotificationDto privateNotificationDto) {
        PrivateNotification privateNotification = privateNotificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("The private notification hasn't been existed ! "));

        privateNotification.setTitle(privateNotificationDto.getTitle());
        privateNotification.setContent(privateNotificationDto.getContent());
        privateNotification.setUpdatedAt(new Date());

        return privateNotificationRepository.save(privateNotification);
    }

    @Override
    public boolean deletePrivateNotification(int id) {
        try {
            privateNotificationRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
