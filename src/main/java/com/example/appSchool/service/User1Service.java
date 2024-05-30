package com.example.appSchool.service;

import com.example.appSchool.model.Professor;
import com.example.appSchool.model.Student;
import com.example.appSchool.model.User1;
import com.example.appSchool.model.dto.User1Dto;
import com.example.appSchool.model.dto.UserUpdateDTO;
import com.example.appSchool.repository.ProfessorRepository;
import com.example.appSchool.repository.StudentRepository;
import com.example.appSchool.repository.User1Repository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor

public class User1Service {
    public final User1Repository user1Repository;
    public final ProfessorRepository professorRepository;
    public final StudentRepository studentRepository;

    public User1Dto getById(Long id){
        User1 user1 = user1Repository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Not found!"));
        return new User1Dto(user1.getId(), user1.getUsername(), user1.getPassword());
    }

    public void editProperties(Long userId, UserUpdateDTO userUpdateDTO) {
        Optional<User1> optionalUser = user1Repository.findById(userId);
        if (!optionalUser.isPresent()) {
            throw new EntityNotFoundException("User not found with id: " + userId);
        }
        User1 user = optionalUser.get();

        updateUserProperties(user, userUpdateDTO);

        user1Repository.save(user);

        Optional<Student> optionalStudent = studentRepository.findByUser1(user);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            updateStudentProperties(student, userUpdateDTO);
            studentRepository.save(student);
            return;
        }

        Optional<Professor> optionalProfessor = professorRepository.findByUser1(user);
        if (optionalProfessor.isPresent()) {
            Professor professor = optionalProfessor.get();
            updateProfessorProperties(professor, userUpdateDTO);
            professorRepository.save(professor);
            return;
        }

        throw new IllegalStateException("User is neither a student nor a professor");
    }

    private void updateUserProperties(User1 user, UserUpdateDTO userUpdateDTO) {
    }

    private void updateStudentProperties(Student student, UserUpdateDTO userUpdateDTO) {
        if (userUpdateDTO.getFirstName() != null) {
            student.setFirstName(userUpdateDTO.getFirstName());
        }
        if (userUpdateDTO.getLastName() != null) {
            student.setLastName(userUpdateDTO.getLastName());
        }
    }

    private void updateProfessorProperties(Professor professor, UserUpdateDTO userUpdateDTO) {
        if (userUpdateDTO.getFirstName() != null) {
            professor.setFirstName(userUpdateDTO.getFirstName());
        }
        if (userUpdateDTO.getLastName() != null) {
            professor.setLastName(userUpdateDTO.getLastName());
        }
    }



}







//    public User1 createUser(String username, String password) {
//        User1 user1 = new User1();
//
//        user1.setUsername(username);
//        user1.setPassword(password);
//
//        user1.setRole(Rolee.PROFESSOR);
//        user1 = user1Repository.save(user1);
//
//        return user1;
//    }
//    public boolean login(String username, String password) {
//        Optional<User1> userOptional = user1Repository.findByUsername(username);
//
//        return userOptional.isPresent() && userOptional.get().getPassword().equals(password);
//    }






