package com.example.appSchool.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class User1Dto {
    private Long id;
    private String username;
    private String password;

    public User1Dto(Long id,String username,String password){
        this.id = id;
        this.username = username;
        this.password = password;
    }
}
