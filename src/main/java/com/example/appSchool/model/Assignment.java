package com.example.appSchool.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;


@Data

@Entity
@Table(name="assignment")
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String assignmentName;
    private String exercises;
    private String finalExam;
    @ManyToOne
    @JoinColumn(name = "professorId")
    @JsonIgnore
    private Professor professor;


    public Assignment() {

    }

    public Long getAssignmentId() {
        return id;
    }

    public void setAssignmentId(Long assignmentId) {
        this.id = id;
    }



    public String getExercises()
    {
        return exercises;
    }

    public void setExercises(String exercises) {

        this.exercises = exercises;
    }

    public String getFinalExam() {
        return finalExam;
    }

    public void setFinalExam(String finalExam) {

        this.finalExam = finalExam;
    }

    public Professor getProfessor() {

        return professor;
    }

    public void setProfessor(Professor professor) {

        this.professor = professor;
    }
}
