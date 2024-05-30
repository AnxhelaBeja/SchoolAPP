package com.example.appSchool.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class AssignmentDto {
    private Long assignmentId;
    private String assignmentName;
    private String exercises;
    private String finalExam;
    private Long professorId;





    public AssignmentDto() {
    }


    public AssignmentDto(Long assignmentId, String assignmentName, String exercises, String finalExam, Long professorId ) {
        this.assignmentId = assignmentId;
        this.assignmentName = assignmentName;
        this.exercises = exercises;
        this.finalExam = finalExam;
        this.professorId = professorId;


    }

    public Long getProfessorId() {
       return assignmentId;
    }
}
