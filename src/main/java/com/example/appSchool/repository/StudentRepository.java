package com.example.appSchool.repository;

import com.example.appSchool.model.Student;
import com.example.appSchool.model.StudentAssignment;
import com.example.appSchool.model.User1;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

  public interface StudentRepository extends JpaRepository<Student, Long> {
      Optional<Student> findByUser1(User1 user);

  }

