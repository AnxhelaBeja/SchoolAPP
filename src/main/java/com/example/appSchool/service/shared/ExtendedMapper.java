package com.example.appSchool.service.shared;

public interface ExtendedMapper <T, K, E extends Object> extends Mapper<T, E> {

    public K toExtendedDto(E entity);

    E toEntity(K extendedDto);
}

