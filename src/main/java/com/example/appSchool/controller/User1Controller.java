package com.example.appSchool.controller;

import com.example.appSchool.model.dto.User1Dto;
import com.example.appSchool.model.dto.UserUpdateDTO;
import com.example.appSchool.service.User1Service;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
  @RequiredArgsConstructor
  @RequestMapping("/api/v1/User1")
  public class User1Controller {
    private final User1Service user1Service;
      @GetMapping("/{id}")
      public ResponseEntity<User1Dto> getById(@PathVariable Long id){
         return ResponseEntity.ok(user1Service.getById(id));
      }

      @PutMapping("/{userId}")
      public ResponseEntity<String> updateUserProperties(
              @PathVariable Long userId,
              @RequestBody UserUpdateDTO userUpdateDTO
      ) {
        try {
          user1Service.editProperties(userId, userUpdateDTO);
          return ResponseEntity.ok("User updated successfully.");
        } catch (EntityNotFoundException e) {
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
      }
    }