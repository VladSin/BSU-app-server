package com.example.bsumts.controller;

import com.example.bsumts.convert.EntityToResponse;
import com.example.bsumts.dto.RoleResponse;
import com.example.bsumts.entity.auth.RoleEntity;
import com.example.bsumts.service.RoleService;
import com.example.bsumts.type.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/role")
public class RoleController {

    private final RoleService roleService;
    private final EntityToResponse entityToResponse;


    @PostMapping("/api/v1/new")
    public ResponseEntity<List<RoleResponse>> addAllRole() {
        roleService.save(new RoleEntity(null, RoleType.ADMIN.name(), null));
        roleService.save(new RoleEntity(null, RoleType.USER.name(), null));
        List<RoleResponse> responses = new ArrayList<>();
        for (RoleEntity role: roleService.getAll()) {
            responses.add(entityToResponse.roleToResponse(role));
        }
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("api/v1/all")
    public ResponseEntity<List<RoleResponse>> getRoles() {
        List<RoleResponse> responses = new ArrayList<>();
        for (RoleEntity role: roleService.getAll()) {
            responses.add(entityToResponse.roleToResponse(role));
        }
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

}
