package com.example.appSchool.service;

import com.example.appSchool.model.Role;
import com.example.appSchool.model.dto.RoleDto;
import com.example.appSchool.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    public RoleDto getById(Long id){
        Role role = roleRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Role not found."));
        return  new RoleDto(role.getId(), role.getName());
    }

}
