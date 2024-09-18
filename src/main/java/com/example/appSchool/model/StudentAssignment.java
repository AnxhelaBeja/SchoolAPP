package com.example.appSchool.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@Entity
@Table(name="student_assignment")
public class StudentAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Transient
//    private String encryptedGrade;

    @Column()
    private int grade;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "assignment_id")
    private Assignment assignment;
    private LocalDate assignmentDate;
    private LocalDate notificationDate;

    @Transient
    private  Double avg;


    public StudentAssignment() {

    }

    public StudentAssignment(Long id, int grade, Student student, Assignment assignment) {
        this.id = id;
        this.grade = grade;
        this.student = student;
        this.assignment = assignment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public LocalDate getAssignmentDate() {
        return assignmentDate;
    }

    public void setAssignmentDate(LocalDate assignmentDate) {
        this.assignmentDate = assignmentDate;
    }

    public LocalDate getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(LocalDate notificationDate) {
        this.notificationDate = notificationDate;
    }
//    public int getGrade() {
//        if (this.encryptedGrade != null) {
//            return GradeUtils.decryptGrade(this.encryptedGrade);
//        }
//        return 0;
//    }
//
//    public void setGrade(int grade) {
//        this.encryptedGrade = GradeUtils.encryptGrade(grade);
//    }
}
