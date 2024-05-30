package com.example.appSchool.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class ProfessorDto {
    private Long id;
    private String firstName;
    private  String lastName;

    public ProfessorDto(Long professorId, String firstName, String lastName){
        this.id=id;
        this.firstName=firstName;
        this.lastName=lastName;
    }

}
