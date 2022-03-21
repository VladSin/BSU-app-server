package com.example.bsumts.service;

import com.example.bsumts.entity.auth.RoleEntity;

import java.util.List;
import java.util.UUID;

public interface RoleService {

    RoleEntity save(RoleEntity role);

    RoleEntity get(UUID id);

    RoleEntity getByName(String name);

    List<RoleEntity> getAll();

}
