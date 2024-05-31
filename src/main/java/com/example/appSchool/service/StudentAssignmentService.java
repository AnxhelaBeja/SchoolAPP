package com.example.appSchool.service;

import com.example.appSchool.model.Assignment;
import com.example.appSchool.model.Student;
import com.example.appSchool.model.StudentAssignment;
import com.example.appSchool.model.User1;
import com.example.appSchool.model.dto.StudentAssignmentDto;
import com.example.appSchool.repository.AssignmentRepository;
import com.example.appSchool.repository.StudentAssignmentRepository;
import com.example.appSchool.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.util.Optional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class StudentAssignmentService {

    public final StudentAssignmentRepository studentAssignmentRepository;
    private final StudentRepository studentRepository;
    private final AssignmentRepository assignmentRepository;
    private final JavaMailSender javaMailSender;


    public StudentAssignment addAssignmentWithStudent(StudentAssignmentDto studentAssignmentDto) {
        StudentAssignment studentAssignment = new StudentAssignment();

        Student student = studentRepository.findById(studentAssignmentDto.getStudentId())
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + studentAssignmentDto.getStudentId()));
        studentAssignment.setStudent(student);

        Assignment assignment = assignmentRepository.findById(studentAssignmentDto.getAssignmentId())
                .orElseThrow(() -> new EntityNotFoundException("Assignment not found with id: " + studentAssignmentDto.getAssignmentId()));
        studentAssignment.setAssignment(assignment);

        studentAssignmentRepository.save(studentAssignment);

        return studentAssignment;
    }

    public StudentAssignment addGradeToAssignment(Long studentAssignmentId, int grade) {
        StudentAssignment studentAssignment = studentAssignmentRepository.findById(studentAssignmentId)
                .orElseThrow(() -> new EntityNotFoundException("Student assignment not found with id: " + studentAssignmentId));

        studentAssignment.setGrade(grade);
        studentAssignmentRepository.save(studentAssignment);
        sendEmailNotification(studentAssignment);

        return studentAssignment;
    }


    private void sendEmailNotification(StudentAssignment studentAssignment) {
        Student student = studentAssignment.getStudent();
        Assignment assignment = studentAssignment.getAssignment();
        String email = student.getEmail();
        String subject = "Njoftim për Notën";
        String message = String.format("I nderuar %s, ju keni marrë një notë prej %d për detyrën: %s",
                student.getUser1().getUsername(), studentAssignment.getGrade(), assignment.getAssignmentName());

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        javaMailSender.send(mailMessage);
    }




    public ResponseEntity<Map<String, Object>> getAverageGradeByStudentId(Long studentId) {
        Optional<Student> optionalStudent = studentRepository.findById(studentId);

        if (optionalStudent.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Student not found with id: " + studentId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Student student = optionalStudent.get();
        User1 user = student.getUser1();

        List<StudentAssignment> studentAssignments = studentAssignmentRepository.findByStudentId(studentId);

        if (studentAssignments.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("student", Map.of("username", user != null ? user.getUsername() : null, "email", student.getEmail()));
            response.put("average", null);
            return ResponseEntity.ok(response);
        }

        double totalGrade = 0.0;
        for (StudentAssignment studentAssignment : studentAssignments) {
            totalGrade += studentAssignment.getGrade();
        }
        double averageGrade = totalGrade / studentAssignments.size();
        student.setAverage(averageGrade);
        studentRepository.save(student);

        Map<String, Object> response = new HashMap<>();
        response.put("student", Map.of("username", user != null ? user.getUsername() : null, "email", student.getEmail()));
        response.put("average", averageGrade);
        return ResponseEntity.ok(response);
    }



    public String getAverageGradeForAssignmentByProfessor(Long assignmentId, Long professorId) {
        List<StudentAssignment> studentAssignments = studentAssignmentRepository.findByAssignmentIdAndAssignmentProfessorId(assignmentId, professorId);
        if (studentAssignments.isEmpty()) {
            return String.format("There are no grades for assignment with ID %d from professor with ID %d.", assignmentId, professorId);
        }

        double totalGrade = 0.0;
        for (StudentAssignment studentAssignment : studentAssignments) {
            totalGrade += studentAssignment.getGrade();
        }
        double averageGrade = totalGrade / studentAssignments.size();
        return String.format("\n" +
                "The average for assignment with ID %d from professor with ID %d is: %.2f", assignmentId, professorId, averageGrade);
    }

    public Optional<Integer> getGradeById(Long studentAssignmentId) {
        return studentAssignmentRepository.findById(studentAssignmentId)
                .map(StudentAssignment::getGrade);
    }

    public Map<String, Integer> getGradesByStudentId(Long studentId) {
        List<StudentAssignment> studentAssignments = studentAssignmentRepository.findAllByStudentId(studentId);
        return studentAssignments.stream()
                .collect(Collectors.toMap(
                        sa -> sa.getAssignment().getAssignmentName(),
                        StudentAssignment::getGrade
                ));
    }

    public Map<String, Object> getAverageAndLetterGrade(Long studentId) {
        List<StudentAssignment> studentAssignments = studentAssignmentRepository.findAllByStudentId(studentId);

        if (studentAssignments.isEmpty()) {
            throw new EntityNotFoundException("No assignments found for student with id: " + studentId);
        }

        double averageGrade = studentAssignments.stream()
                .mapToInt(StudentAssignment::getGrade)
                .average()
                .orElse(0.0);

        String letterGrade = calculateLetterGrade(averageGrade);

        Map<String, Object> result = new HashMap<>();
        result.put("averageGrade", averageGrade);
        result.put("letterGrade", letterGrade);

        return result;
    }

    private String calculateLetterGrade(double averageGrade) {
        if (averageGrade >= 90) {
            return "A";
        } else if (averageGrade >= 80) {
            return "B";
        } else if (averageGrade >= 70) {
            return "C";
        } else if (averageGrade >= 60) {
            return "D";
        } else {
            return "F";
        }
    }
}





