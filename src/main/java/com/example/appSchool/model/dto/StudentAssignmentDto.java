package com.example.appSchool.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
public class StudentAssignmentDto {

    private Long studentId;
    private Long assignmentId;

    public StudentAssignmentDto(Long studentId, Long assignmentId) {
        this.studentId = studentId;
        this.assignmentId = assignmentId;
    }

}
