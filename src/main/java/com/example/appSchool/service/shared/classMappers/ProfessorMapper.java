package com.example.appSchool.service.shared.classMappers;

import com.example.appSchool.model.Professor;
import com.example.appSchool.model.dto.ProfessorDto;
import com.example.appSchool.model.dto.ProfessorExtendedDto;
import com.example.appSchool.service.shared.ExtendedMapper;
import org.springframework.stereotype.Component;

@Component
public class ProfessorMapper implements ExtendedMapper<ProfessorDto, ProfessorExtendedDto, Professor> {

    @Override
    public ProfessorDto toDto(Professor entity) {
        return ProfessorDto.builder()
                .id(entity.getId())
                        .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .build();
    }
   @Override
    public  ProfessorExtendedDto toExtendedDto(Professor entity) {
        return ProfessorExtendedDto.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .build();
    }

    @Override
    public Professor toEntity(ProfessorExtendedDto dto) {
        return new Professor().builder()
                .id(dto.getId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .build();

    }}

