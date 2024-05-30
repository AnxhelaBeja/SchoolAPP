package com.example.appSchool.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class RoleDto {
    private Long id;
    private String firstName;

    public RoleDto(Long id, String firstName){
        this.id=id;
        this.firstName=firstName;
    }

}
