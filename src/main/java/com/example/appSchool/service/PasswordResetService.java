package com.example.appSchool.service;
import com.example.appSchool.model.Student;
import com.example.appSchool.model.dto.PasswordResetRequestDTO;
import com.example.appSchool.repository.StudentRepository;
import com.example.appSchool.repository.User1Repository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class PasswordResetService {

    private final StudentRepository studentRepository;
    private final JavaMailSender javaMailSender;
    private final PasswordEncoder passwordEncoder;
    private final User1Repository user1Repository;

    @Value("app.password.reset.url=http://example.com/reset-password\n")
    private String resetUrl;

    private final Map<String, PasswordResetToken> tokenStore = new HashMap<>();

    public PasswordResetService(StudentRepository studentRepository,
                                JavaMailSender javaMailSender,
                                PasswordEncoder passwordEncoder,
                                User1Repository user1Repository) {
        this.studentRepository = studentRepository;
        this.javaMailSender = javaMailSender;
        this.passwordEncoder = passwordEncoder;
        this.user1Repository = user1Repository;
    }

    @Transactional
    public void sendPasswordRecoveryEmail(String email) {
        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));

        String token = UUID.randomUUID().toString();
        LocalDateTime expiryDate = LocalDateTime.now().plusHours(24);

        tokenStore.put(token, new PasswordResetToken(token, student, expiryDate));

        String recoveryLink = resetUrl + "?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Recovery");
        message.setText("Hello, " +
                "\n\nTo reset your password, please click the link below:\n" + recoveryLink +
                "\n\nIf you did not request a password reset, please ignore this email.");

        javaMailSender.send(message);
    }

    @Transactional
    public void resetPassword(PasswordResetRequestDTO requestDTO) {
        String token = requestDTO.getToken();
        String newPassword = requestDTO.getNewPassword();

        PasswordResetToken passwordResetToken = tokenStore.get(token);

        if (passwordResetToken == null || passwordResetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Invalid or expired password reset token");
        }

        Student student = passwordResetToken.getStudent();
        student.getUser1().setPassword(passwordEncoder.encode(newPassword));
        user1Repository.save(student.getUser1());
        studentRepository.save(student);

        tokenStore.remove(token);
    }

    private static class PasswordResetToken {
        private String token;
        private Student student;
        private LocalDateTime expiryDate;

        public PasswordResetToken(String token, Student student, LocalDateTime expiryDate) {
            this.token = token;
            this.student = student;
            this.expiryDate = expiryDate;
        }

        public String getToken() {
            return token;
        }

        public Student getStudent() {
            return student;
        }

        public LocalDateTime getExpiryDate() {
            return expiryDate;
        }
    }
}
