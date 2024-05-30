package com.example.appSchool.controller;
import com.example.appSchool.model.dto.StudentDto;
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


    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getById(id));
    }






    @PutMapping("/{studentId}/state")
    public ResponseEntity<String> changeStudentState(@PathVariable Long
                                                             studentId, @RequestBody Map<String, Object> requestBody) {
        if (requestBody.containsKey("newState")) {
            boolean newState = (Boolean) requestBody.get("newState");
            studentService.changeStudentState(studentId, newState);
            return ResponseEntity.ok("Student state updated successfully.");
        } else {
            return ResponseEntity.badRequest().body("Missing 'newState' in request body");
        }
    }

}
//    @PostMapping("/state")
//    public ResponseEntity<String> changeStudentStateV2(@RequestBody Map<String, Object> requestBody,RequestEntity request) {
//        if (requestBody.containsKey("newState")) {
//            Long studentId = (Long) request.getBody();
//            boolean newState = (Boolean) requestBody.get("newState");
//            studentService.changeStudentState(studentId, newState);
//            return ResponseEntity.ok("Student state updated successfully.");
//        } else {
//            return ResponseEntity.badRequest().body("Missing 'newState' in request body");
//        }












