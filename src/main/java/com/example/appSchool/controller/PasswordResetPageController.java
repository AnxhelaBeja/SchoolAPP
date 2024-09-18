package com.example.appSchool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PasswordResetPageController {

    @GetMapping("/reset-password")
    public String getResetPasswordPage() {
        return "reset-password";
    }
}

