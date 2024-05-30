package com.example.appSchool.model.dto;

import com.example.appSchool.model.Professor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Builder
@Getter
@Setter
public class StudentDto {
    private Long studentId;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private Professor professor;
    //private double average;
 public StudentDto(Long studentId, String firstName, String lastName,
                   String email, String address, Professor professor){
     this.studentId=studentId;
     this.firstName=firstName;
     this.lastName=lastName;
     this.email=email;
     this.address=address;
     this.professor=professor;

 }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
