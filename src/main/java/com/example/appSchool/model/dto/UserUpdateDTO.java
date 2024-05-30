package com.example.appSchool.model.dto;

public class UserUpdateDTO {
    private String firstName;
    private String lastName;

    public UserUpdateDTO() {
    }

    public UserUpdateDTO(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
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
