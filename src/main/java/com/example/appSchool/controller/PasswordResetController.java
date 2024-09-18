package com.example.appSchool.controller;

import com.example.appSchool.model.dto.PasswordResetRequestDTO;
import com.example.appSchool.service.PasswordResetService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reset-password")
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    public PasswordResetController(PasswordResetService passwordResetService) {
        this.passwordResetService = passwordResetService;
    }

    @PostMapping("/send-email")
    public ResponseEntity<Void> sendPasswordRecoveryEmail(@RequestParam String email) {
        try {
            passwordResetService.sendPasswordRecoveryEmail(email);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(@RequestBody PasswordResetRequestDTO requestDTO) {
        try {
            passwordResetService.resetPassword(requestDTO);
            return ResponseEntity.ok("Password reset successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}


//    @PostMapping("/reset")
//    public ResponseEntity<String> resetPassword(@RequestBody PasswordResetRequestDTO requestDTO) {
//        try {
//            log.info("Resetting password for token: {}", requestDTO.getToken());
//            passwordResetService.resetPassword(requestDTO);
//            return ResponseEntity.ok("Password reset successfully.");
//        } catch (IllegalArgumentException e) {
//            log.error("Error resetting password: {}", e.getMessage());
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }