package com.example.appSchool.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
public class StudentAssignmentDto {

    private Long studentId;
    private Long assignmentId;
    private LocalDate assignmentDate;
    @JsonProperty
    private LocalDate notificationDate;
    private LocalTime assignmentTime;


    public StudentAssignmentDto(Long studentId, Long assignmentId, LocalDate assignmentDate,LocalDate notificationDate , LocalTime assignmentTime) {
        this.studentId = studentId;
        this.assignmentId = assignmentId;
        this.assignmentDate = assignmentDate;
        this.notificationDate = notificationDate;
        this.assignmentTime = assignmentTime;

    }
}
