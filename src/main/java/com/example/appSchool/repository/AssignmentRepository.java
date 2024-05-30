package com.example.appSchool.repository;

import com.example.appSchool.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
}


