package com.example.appSchool.service.shared;

public interface Mapper <T, E extends Object> {
    public T toDto(E entity);
}
