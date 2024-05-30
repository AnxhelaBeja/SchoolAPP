package com.example.appSchool.service;

import com.example.appSchool.model.*;
import com.example.appSchool.model.dto.ProfessorDto;
import com.example.appSchool.model.dto.ProfessorExtendedDto;
import com.example.appSchool.model.dto.StudentExtendedDto;
import com.example.appSchool.repository.AssignmentRepository;
import com.example.appSchool.repository.ProfessorRepository;
import com.example.appSchool.repository.StudentRepository;
import com.example.appSchool.repository.User1Repository;
import com.example.appSchool.service.shared.classMappers.ProfessorMapper;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service

public class ProfessorService {
    private final ProfessorRepository professorRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final User1Repository user1Repository;


    public ProfessorService(ProfessorRepository professorRepository, StudentRepository studentRepository, PasswordEncoder passwordEncoder, User1Repository user1Repository) {
        this.professorRepository = professorRepository;
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
        this.user1Repository = user1Repository;
    }
    public ProfessorDto getById(Long id){
        Professor professor = professorRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Professor not found!"));
        return new ProfessorDto(professor.getId(),
                professor.getFirstName(),
                professor.getLastName());
    }

    @Transactional
    public void createStudent(RequestWrapper requestWrapper) {
        String email = requestWrapper.getEmail();

        User1 user = new User1();
        user.setUsername(requestWrapper.getEmail());
        user.setRole(Rolee.STUDENT);

        String password = requestWrapper.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);
        user1Repository.save(user);

        Student student = new Student();
        student.setFirstName(requestWrapper.getFirstname());
        student.setLastName(requestWrapper.getLastname());
        student.setEmail(requestWrapper.getEmail());
        student.setAddress(requestWrapper.getAddress());
        student.setUser1(user);

        studentRepository.save(student);
    }









//    @Transactional
//    public void createStudent(Student student) {
//
//        String email = student.getEmail();
//
//        User1 user = new User1();
//        user.setUsername(student.getEmail());
//        user.setRole(Rolee.STUDENT);
//
//        String password = student.getPassword();
//        String encodedPassword = passwordEncoder.encode(password);
//        user.setPassword(encodedPassword);
//        user1Repository.save(user);
//
//        student.setUser1(user);
//        student.setAddress(student.getAddress());
//
//        studentRepository.save(student);
//    }



//    public ResponseEntity<String> editStudent(Long studentId, StudentExtendedDto studentExtendedDto) {
//        try {
//            if (studentId == null) {
//                throw new IllegalArgumentException("Student ID cannot be null");
//            }
//
//            Optional<Student> studentOptional = studentRepository.findById(studentId);
//            if (!studentOptional.isPresent()) {
//                return ResponseEntity.notFound().build();
//            }
//
//            Student student = studentOptional.get();
//            if (studentExtendedDto != null) {
//                if (studentExtendedDto.getFirstName() != null) {
//                    student.setFirstName(studentExtendedDto.getFirstName());
//                }
//                if (studentExtendedDto.getLastName() != null) {
//                    student.setLastName(studentExtendedDto.getLastName());
//                }
//                if (studentExtendedDto.getEmail() != null) {
//                    student.setEmail(studentExtendedDto.getEmail());
//                }
//                if (studentExtendedDto.getAddress() != null) {
//                    student.setAddress(studentExtendedDto.getAddress());
//                }
//            }
//
//            studentRepository.save(student);
//            return ResponseEntity.ok("Student updated successfully!");
//        } catch (IllegalArgumentException ex) {
//            return ResponseEntity.badRequest().body(ex.getMessage());
//        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating student.");
//        }


}






























