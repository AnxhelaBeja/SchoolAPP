package com.example.appSchool.repository;

import com.example.appSchool.model.Professor;
import com.example.appSchool.model.User1;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    Optional<Professor> findByUser1(User1 user);

}
