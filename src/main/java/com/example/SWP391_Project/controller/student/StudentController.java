package com.example.SWP391_Project.controller.student;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudentController {
    @GetMapping("/student")
    public String studentDashboard() {
        return "student-dashboard";
    }

    //ĐỂ PHẦN MANAGER Ở ĐÂY TẠM
    @GetMapping("/manager")
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
    @GetMapping("/centerHome")
    public String centerHome() {
        return "centerDetail";
    }



}
