package com.example.appSchool.model;

import jakarta.persistence.*;
import lombok.*;

@Data

@Entity
@Table(name="student_assignment")
public class StudentAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column()
    private int grade;

    @ManyToOne
    @JoinColumn(name = "studentId")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "assignmentId")
    private Assignment assignment;

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
}
