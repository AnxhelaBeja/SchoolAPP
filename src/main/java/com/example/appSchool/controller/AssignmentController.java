package com.example.appSchool.controller;

import com.example.appSchool.model.Assignment;
import com.example.appSchool.model.dto.AssignmentDto;
import com.example.appSchool.service.AssignmentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
  @RequiredArgsConstructor
  @RequestMapping("/api/v1/Assignment")
  public class AssignmentController {
      private final AssignmentService assignmentService;

      public ResponseEntity<AssignmentDto> getById(@PathVariable Long id){
          return ResponseEntity.ok(assignmentService.getById(id));
      }
    @PostMapping("/assignments")
    public ResponseEntity<Assignment> addAssignment(@RequestBody AssignmentDto assignmentDto)
            throws EntityNotFoundException {

        Assignment assignment = assignmentService.addAssignment(
                assignmentDto.getAssignmentId(),
                assignmentDto.getAssignmentName(),
                assignmentDto.getExercises(),
                assignmentDto.getFinalExam(),
                assignmentDto.getProfessorId());

        return ResponseEntity.ok(assignment);
    }


}
