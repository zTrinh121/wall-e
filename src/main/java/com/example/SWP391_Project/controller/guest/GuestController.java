package com.example.SWP391_Project.controller.guest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GuestController {

    @GetMapping("/guest")
    public String guestPage() {
        return "guest"; // This should match the name of your Thymeleaf template (guest.html)
    }
}
