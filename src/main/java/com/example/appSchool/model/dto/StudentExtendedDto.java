package com.example.appSchool.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Builder
@Setter
@Getter
public class StudentExtendedDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String address;

public StudentExtendedDto(Long id, String firstName, String lastName, String email, String address){
    this.id=id;
    this.firstName=firstName;
    this.lastName=lastName;
    this.email=email;
    this.address=address;

}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
