package com.example.appSchool.service;

import com.example.appSchool.model.*;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class StudentAssignmentService {

    public final StudentAssignmentRepository studentAssignmentRepository;
    private final StudentRepository studentRepository;
    private final JavaMailSender javaMailSender;
    private final AssignmentRepository assignmentRepository;
    private final GradeUtils gradeUtils;

    @Transactional
    public StudentAssignment addAssignmentWithStudent(StudentAssignmentDto studentAssignmentDto) {
        StudentAssignment studentAssignment = new StudentAssignment();

        Student student = studentRepository.findById(studentAssignmentDto.getStudentId())
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + studentAssignmentDto.getStudentId()));
        studentAssignment.setStudent(student);

        Assignment assignment = assignmentRepository.findById(studentAssignmentDto.getAssignmentId())
                .orElseThrow(() -> new EntityNotFoundException("Assignment not found with id: " + studentAssignmentDto.getAssignmentId()));
        studentAssignment.setAssignment(assignment);

        studentAssignment.setAssignmentDate(studentAssignmentDto.getAssignmentDate());
        studentAssignment.setNotificationDate(studentAssignmentDto.getNotificationDate());

        studentAssignmentRepository.save(studentAssignment);
        sendAssignmentEmail(student.getEmail(), assignment.getAssignmentName(), studentAssignmentDto.getAssignmentDate(), studentAssignmentDto.getAssignmentTime());

        LocalDateTime notificationDateTime = studentAssignmentDto.getNotificationDate().atStartOfDay();
        Instant notificationInstant = notificationDateTime.atZone(ZoneId.systemDefault()).toInstant();

        LocalTime notificationTime = LocalTime.of(11, 30);

        if (LocalDate.now().isEqual(studentAssignmentDto.getNotificationDate()) && LocalTime.now().isAfter(notificationTime)) {
            sendReminderEmail(student.getEmail(), assignment.getAssignmentName());
        }

        return studentAssignment;
    }
    private void sendAssignmentEmail(String studentEmail, String assignmentName, LocalDate assignmentDate, LocalTime assignmentTime) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(studentEmail);
        message.setSubject("New assignment added");
        message.setText("Hello!\n" + "Your new assignment \"" + assignmentName + "\" has been added for the date " + assignmentDate + " at " + assignmentTime + " \n \n Your School,\n" + "Thank you!" );

        javaMailSender.send(message);
    }

    private void sendReminderEmail(String studentEmail, String assignmentName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(studentEmail);
        message.setSubject("Reminder for assignment");
        message.setText("Careful! You have the assignment \"" + assignmentName + "\" for today\n. \n" +
                "Don't forget to do it on time.");

        javaMailSender.send(message);
    }


        public StudentAssignment addGradeToAssignment(Long studentAssignmentId, int grade) {
        if (grade < 0 || grade > 100) {
            throw new IllegalArgumentException("The score must be between 0 and 100.");
        }
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
        String subject = "Grade Notice";
        String message = String.format("Dear %s, You have received a grade of  %d for the assignment: %s",
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
    @Transactional(readOnly = true)
    public List<StudentAssignment> getAssignmentsByStudentAndAcademicYear(Long studentId, int startYear) {
        LocalDate fallStartDate = LocalDate.of(startYear, 9, 1);
        LocalDate springEndDate = LocalDate.of(startYear + 1, 6, 30);

        return studentAssignmentRepository.findAssignmentsByStudentAndDateRange(studentId, fallStartDate, springEndDate);
    }

    @Transactional
    public void sendAcademicYearReport(Long studentId, int startYear) {
        List<StudentAssignment> assignments = getAssignmentsByStudentAndAcademicYear(studentId, startYear);

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + studentId));

        StringBuilder emailContent = new StringBuilder("Hello " + student.getFirstName() + " " + student.getLastName() + ",\n\nHere are your assignments and grades for the academic year " + startYear + "-" + (startYear + 1) + ":\n\n");

        for (StudentAssignment assignment : assignments) {
            emailContent.append("Assignment: ")
                    .append(assignment.getAssignment().getAssignmentName())
                    .append("\nDate: ")
                    .append(assignment.getAssignmentDate())
                    .append("\nGrade: ")
                    .append(assignment.getGrade())
                    .append("\n\n");
        }

        emailContent.append("Best regards,\nYour School");

        sendEmail(student.getEmail(), "Academic Year Report", emailContent.toString());
    }

    private void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }

}





//public StudentAssignment getStudentAssignmentById(Long studentAssignmentId) {
//        return studentAssignmentRepository.findById(studentAssignmentId)
//                .orElseThrow(() -> new EntityNotFoundException("Student assignment not found with id: " + studentAssignmentId));
//    }
//    public StudentAssignment addGradeToAssignment(Long studentAssignmentId, int grade) {
//        if (grade < 0 || grade > 100) {
//            throw new IllegalArgumentException("The score must be between 0 and 100.");
//        }
//
//        String encryptedGrade = gradeUtils.encryptGrade(grade);
//
//        StudentAssignment studentAssignment = studentAssignmentRepository.findById(studentAssignmentId)
//                .orElseThrow(() -> new EntityNotFoundException("Student assignment not found with id: " + studentAssignmentId));
//        studentAssignment.setGrade(grade);
////        studentAssignment.setEncryptedGrade(encryptedGrade);
//        studentAssignmentRepository.save(studentAssignment);
//        sendEmailNotification(studentAssignment);
//
//        return studentAssignment;
//    }
//
//    public int getDecryptedGrade(StudentAssignment studentAssignment) {
//        String encryptedGrade = studentAssignment.getEncryptedGrade();
//
//        int decryptedGrade = gradeUtils.decryptGrade(encryptedGrade);
//
//        return decryptedGrade;
//    }
//    private void sendEmailNotification(StudentAssignment studentAssignment) {
//        Student student = studentAssignment.getStudent();
//        Assignment assignment = studentAssignment.getAssignment();
//        String email = student.getEmail();
//        String subject = "Grade Notice";
//
//        int decryptedGrade = gradeUtils.decryptGrade(studentAssignment.getEncryptedGrade());
//
//        String message = String.format("Dear %s, You have received a grade of  %d for the assignment: %s",
//                student.getUser1().getUsername(), decryptedGrade, assignment.getAssignmentName());
//
//        SimpleMailMessage mailMessage = new SimpleMailMessage();
//        mailMessage.setTo(email);
//        mailMessage.setSubject(subject);
//        mailMessage.setText(message);
//        javaMailSender.send(mailMessage);
//    }