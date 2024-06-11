package com.example.appSchool.service;

import com.example.appSchool.model.*;
import com.example.appSchool.model.dto.ProfessorDto;
import com.example.appSchool.repository.ProfessorRepository;
import com.example.appSchool.repository.StudentRepository;
import com.example.appSchool.repository.User1Repository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;




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
}






























