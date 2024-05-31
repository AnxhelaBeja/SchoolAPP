package com.example.appSchool.controller;

import com.example.appSchool.model.AuthenticationResponse;
import com.example.appSchool.model.User1;
import com.example.appSchool.model.dto.RegisterProfessorRequest;
import com.example.appSchool.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
private final AuthenticationService authService;

    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }
    @PostMapping("/register/auth")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterProfessorRequest request
    ) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login/auth")
    public ResponseEntity <AuthenticationResponse> login(
            @RequestBody User1 request
    ){
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
