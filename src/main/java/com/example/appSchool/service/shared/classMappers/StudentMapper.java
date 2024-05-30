package com.example.appSchool.service.shared.classMappers;


import com.example.appSchool.model.Student;
import com.example.appSchool.model.dto.StudentDto;
import com.example.appSchool.model.dto.StudentExtendedDto;
import com.example.appSchool.service.shared.ExtendedMapper;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper  implements ExtendedMapper<StudentDto, StudentExtendedDto, Student> {

    @Override
    public StudentDto toDto(Student entity) {
         return StudentDto.builder()
                .studentId(entity.getStudentId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .build();
    }
    @Override
    public StudentExtendedDto toExtendedDto(Student entity) {
        return StudentExtendedDto.builder()
                .id(entity.getStudentId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .address(entity.getAddress())
                .build();
    }
    @Override
    public Student toEntity(StudentExtendedDto dto) {
        return new Student().builder()
                .id(dto.getId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .address(dto.getAddress())
                .build();
    }


}
