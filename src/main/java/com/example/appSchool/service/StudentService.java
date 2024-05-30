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

        public void changeStudentState(Long studentId, boolean newState) {

        Student student = studentRepository.findById(studentId).orElse(null);

        if (student != null) {
            student.setState(newState);
            studentRepository.save(student);
        }
    }
}







//    @Transactional
//    public Student createStudent(StudentExtendedDto studentDto) {
//        Optional<Student> existingStudent = studentRepository.findByFirstNameAndLastName(studentDto.getFirstName(), studentDto.getLastName());
//        if (existingStudent.isPresent()) {
//            throw new EntityExistsException("Student with name " + studentDto.getFirstName() + " " + studentDto.getLastName() + " already exists.");
//        }
//
//        User1 newUser = new User1();
//        newUser.setUsername(studentDto.getUsername());
//        newUser.setPassword(studentDto.getPassword());
//        newUser.setRole(Rolee.STUDENT);
//        User1 savedUser = user1Repository.save(newUser);
//
//        Student newStudent = studentMapper.toEntity(studentDto);
//        newStudent.setUser1(savedUser);
//
//        return studentRepository.save(newStudent);
//    }



//    public ResponseEntity<String> updateStudent(Long studentId, StudentExtendedDto studentExtendedDto) {
//        Optional<Student> studentOptional = studentRepository.findById(studentId);
//
//        if (studentOptional.isPresent()) {
//            Student student = studentOptional.get();
//
//            student.setFirstName(studentExtendedDto.getFirstName());
//            student.setLastName(studentExtendedDto.getLastName());
//            student.setEmail(studentExtendedDto.getEmail());
//            student.setAddress(studentExtendedDto.getAddress());
//
//         studentRepository.save(student);
//            return ResponseEntity.ok("Student updated successfully!");
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }











