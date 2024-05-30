package com.example.appSchool.repository;

import com.example.appSchool.model.StudentAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentAssignmentRepository extends JpaRepository<StudentAssignment, Long> {
    List<StudentAssignment> findByStudentId(Long studentId);

    List<StudentAssignment> findByAssignmentIdAndAssignmentProfessorId(Long assignmentId, Long professorId);

    List<StudentAssignment> findAllByStudentId(Long studentId);



}
