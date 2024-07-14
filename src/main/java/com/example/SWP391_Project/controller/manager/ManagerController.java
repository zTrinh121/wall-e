package com.example.SWP391_Project.controller.manager;

import com.example.SWP391_Project.dto.CenterDto;
import com.example.SWP391_Project.dto.CourseDto;
import com.example.SWP391_Project.dto.SlotDto;
import com.example.SWP391_Project.model.*;
import com.example.SWP391_Project.response.CenterDetailResponse;
import com.example.SWP391_Project.response.CourseDetailResponse;
import com.example.SWP391_Project.service.AdminService;
import com.example.SWP391_Project.service.ManagerService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import java.time.Month;
import java.time.Year;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/manager")
public class ManagerController
{
    @Autowired
    ManagerService managerService;
    @GetMapping("/managerHome")
    public String managerHome() {
        return "managerHome";
    }
    //    qlhv = quan li hoc vien
    @GetMapping("/qlhv")
    public String stuManage() {
        return "studentDetail";
    }
    //    tthv = thong tin hoc vien
    @GetMapping("/tthv")
    public String stuInfo() {
        return "studentInfo";
    }
    //    qlgv = quan li giao vien
    @GetMapping("/qlgv")
    public String teachManage() {
        return "teacherDetail";
    }
    //    ttgv = thong tin giao vien
    @GetMapping("/ttgv")
    public String teacherInfo() {
        return "teacherInfo";
    }

    @GetMapping("/noti")
    public String mnoti(){
        return  "managerNoti";
    }
    @GetMapping("/centerHome")
    public String centerHome2() {
        return "centerDetail";
    }
    @GetMapping("/mpost")
    public String mnPost() {
        return "managerPost";
    }
    @GetMapping("/qlkh")
    public String mnCourse() {
        return "courseDetail";
    }
    @GetMapping("/ttkh")
    public String courseInfo() {
        return "courseInfo";
    }
    @GetMapping("/dthu")
    public String revenueMn() {
        return "managerRevenue";
    }


}
