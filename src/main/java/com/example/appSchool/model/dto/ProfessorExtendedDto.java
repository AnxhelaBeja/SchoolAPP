package com.example.appSchool.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Builder
@Setter
@Getter
public class ProfessorExtendedDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;


    public ProfessorExtendedDto(Long id, String firstName, String lastName, String email ){
        this.id=id;
        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
