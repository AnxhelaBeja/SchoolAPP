package com.example.appSchool.repository;

import com.example.appSchool.model.Assignment;
import com.example.appSchool.model.StudentAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface StudentAssignmentRepository extends JpaRepository<StudentAssignment, Long> {
    List<StudentAssignment> findByStudentId(Long studentId);

    List<StudentAssignment> findByAssignmentIdAndAssignmentProfessorId(Long assignmentId, Long professorId);

    List<StudentAssignment> findAllByStudentId(Long studentId);
    @Query("SELECT sa FROM StudentAssignment sa WHERE sa.student.id = :studentId AND sa.assignmentDate BETWEEN :startDate AND :endDate")
    List<StudentAssignment> findAssignmentsByStudentAndDateRange(@Param("studentId") Long studentId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
