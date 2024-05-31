package com.example.appSchool.controller;

import com.example.appSchool.model.StudentAssignment;
import com.example.appSchool.model.dto.StudentAssignmentDto;
import com.example.appSchool.service.StudentAssignmentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@RestController
  @RequiredArgsConstructor
  @RequestMapping("/api/v1/StudentAssignment")
  public class StudentAssignmentController {

    private final StudentAssignmentService studentAssignmentService;


//    @GetMapping("/{id}")
//    public ResponseEntity<StudentAssignmentDto> getStudentAssignmentById(@PathVariable Long id) {
//        StudentAssignmentDto studentAssignmentDto = studentAssignmentService.getById(id);
//        return ResponseEntity.ok(studentAssignmentDto);
//    }
//      public ResponseEntity<StudentAssignmentDto> getById(@PathVariable Long id){
//          return ResponseEntity.ok(studentAssignmentService.getById(id));
//      }

    @PostMapping("/addAssignmentWithStudent")
    public ResponseEntity<StudentAssignment> addAssignmentWithStudent(@RequestBody StudentAssignmentDto studentAssignmentDto)
            throws EntityNotFoundException {

        StudentAssignment studentAssignment = studentAssignmentService.addAssignmentWithStudent(studentAssignmentDto);
        return ResponseEntity.ok(studentAssignment);
    }
    @PostMapping("/{studentAssignmentId}/grade")
    public ResponseEntity<StudentAssignment> addGradeToAssignment(
            @PathVariable Long studentAssignmentId,
            @RequestParam int grade) {
        StudentAssignment updatedAssignment = studentAssignmentService.addGradeToAssignment(studentAssignmentId, grade);
        return ResponseEntity.ok(updatedAssignment);
    }

    @GetMapping("/{studentId}/averageGrade")
    public ResponseEntity<Map<String, Object>> getAverageGradeByStudentId(@PathVariable Long studentId) {
        return studentAssignmentService.getAverageGradeByStudentId(studentId);
    }

    @GetMapping("/{assignmentId}/professor/{professorId}/averageGrade")
    public ResponseEntity<String> getAverageGradeForAssignmentByProfessor(
            @PathVariable Long assignmentId,
            @PathVariable Long professorId) {
        String responseMessage = studentAssignmentService.getAverageGradeForAssignmentByProfessor(assignmentId, professorId);
        return ResponseEntity.ok(responseMessage);
    }

    @GetMapping("/{studentAssignmentId}/grade")
    public ResponseEntity<Map<String, Integer>> getGradeById(@PathVariable Long studentAssignmentId) {
        Optional<Integer> grade = studentAssignmentService.getGradeById(studentAssignmentId);
        Map<String, Integer> responseBody = new HashMap<>();
        grade.ifPresent(g -> responseBody.put("grade", g));
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/{studentId}/grades")
    public ResponseEntity<Map<String, Integer>> getGradesByStudentId(@PathVariable Long studentId) {
        Map<String, Integer> grades = studentAssignmentService.getGradesByStudentId(studentId);
        return ResponseEntity.ok(grades);
    }

    @GetMapping("/{studentId}/average-grade")
    public ResponseEntity<Map<String, Object>> getAverageAndLetterGrade(@PathVariable Long studentId) {
        Map<String, Object> result = studentAssignmentService.getAverageAndLetterGrade(studentId);
        return ResponseEntity.ok(result);
    }
  }