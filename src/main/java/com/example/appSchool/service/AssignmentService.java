package com.example.appSchool.service;

import com.example.appSchool.model.Assignment;
import com.example.appSchool.model.Professor;
import com.example.appSchool.model.Student;
import com.example.appSchool.model.dto.AssignmentDto;
import com.example.appSchool.model.dto.StudentAssignmentDto;
import com.example.appSchool.repository.AssignmentRepository;
import com.example.appSchool.repository.ProfessorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor

public class AssignmentService {
    public final AssignmentRepository assignmentRepository;
    private final ProfessorRepository professorRepository;


    public AssignmentDto getById(Long id) {
        Assignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Assignment not found."));
        return new AssignmentDto(assignment.getAssignmentId(),assignment.getAssignmentName(),
                assignment.getExercises(),assignment.getFinalExam(), assignment.getId());
    }

    @Transactional
    public Assignment addAssignment(Long assignmentId,String assignmentName, String exercises, String finalExam, Long professorId) {

        Assignment assignment = new Assignment();
        assignment.setAssignmentId(assignmentId);
        assignment.setAssignmentName(assignmentName);
        assignment.setExercises(exercises);
        assignment.setFinalExam(finalExam);
        Professor professor = professorRepository.findById(professorId)
                .orElseThrow(() -> new EntityNotFoundException("Professor not found with id: " + professorId));

        assignment.setProfessor(professor);

        assignmentRepository.save(assignment);
        return assignment;
    }
}
