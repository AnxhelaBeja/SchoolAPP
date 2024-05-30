package com.example.appSchool.controller;

import com.example.appSchool.model.dto.RoleDto;
import com.example.appSchool.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

  @RestController
  @RequiredArgsConstructor

  @RequestMapping("/api/v1/Role")
  public class RoleController {

      private final RoleService roleService;

      public ResponseEntity<RoleDto> getBYId(@PathVariable Long id){
          return ResponseEntity.ok(roleService.getById(id));
      }

}
