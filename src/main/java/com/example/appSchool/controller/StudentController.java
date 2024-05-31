package com.example.appSchool.controller;
import com.example.appSchool.model.dto.StudentDto;
import com.example.appSchool.service.JwtService;
import com.example.appSchool.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Map;


@RestController
  @RequiredArgsConstructor
  @RequestMapping("/api/v1/Student")
  public class StudentController {


    private final StudentService studentService;
    private final JwtService jwtService;


    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getById(id));
    }

    @PutMapping("/state")
    public ResponseEntity<String> changeStudentState(@RequestHeader("Authorization") String authorizationHeader, @RequestBody Map<String, Object> requestBody) {
        String token = authorizationHeader.substring(7);
        String username = jwtService.extractUsername(token);

        if (requestBody.containsKey("newState")) {
            boolean newState = (Boolean) requestBody.get("newState");
            studentService.changeStudentState(username, newState);
            return ResponseEntity.ok("Student state updated successfully.");
        } else {
            return ResponseEntity.badRequest().body("Missing 'newState' in request body");
        }
    }
  }