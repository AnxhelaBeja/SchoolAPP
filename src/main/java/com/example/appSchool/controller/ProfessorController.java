package com.example.appSchool.controller;

import com.example.appSchool.model.RequestWrapper;
import com.example.appSchool.model.Student;
import com.example.appSchool.model.dto.ProfessorDto;
import com.example.appSchool.service.ProfessorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/Professor")
public class ProfessorController {

    private final ProfessorService professorService;
    @GetMapping("/{id}")
    public ResponseEntity<ProfessorDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(professorService.getById(id));
    }

    @PostMapping("/professors/students")
    public ResponseEntity<String> createStudentForProfessor(@RequestBody RequestWrapper requestWrapper) {
        try {
            professorService.createStudent(requestWrapper);
            return ResponseEntity.ok("The student was successfully created and connected to the professor.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("A problem occurred while creating the student: " + e.getMessage());
        }
    }





//    @PostMapping("/professors/students")
//    public ResponseEntity<String> createStudentForProfessor(@RequestBody Student student) {
//        try {
//            professorService.createStudent(student);
//            return ResponseEntity.ok("The student was successfully created and connected to the professor.");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("A problem occurred while creating the student: " + e.getMessage());
//        }
//    }
}







