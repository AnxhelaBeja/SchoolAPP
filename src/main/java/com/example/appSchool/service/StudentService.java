package com.example.appSchool.service;

import com.example.appSchool.model.*;
import com.example.appSchool.model.dto.StudentDto;
import com.example.appSchool.repository.StudentRepository;
import com.example.appSchool.repository.User1Repository;
import com.example.appSchool.service.shared.classMappers.StudentMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final User1Repository user1Repository;


    public StudentDto getById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found!"));
        return new StudentDto(student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getAddress(),
                student.getProfessor()
        );

    }

    public void changeStudentState(String username, boolean newState) {
        User1 user = user1Repository.findByUsername(username).orElseThrow();
        Student student = studentRepository.findByUser1(user).orElse(null);

        if (student != null) {
            student.setState(newState);
            studentRepository.save(student);
        }
    }
}



















