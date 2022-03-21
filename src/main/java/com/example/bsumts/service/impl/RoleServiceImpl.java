package com.example.bsumts.service.impl;

import com.example.bsumts.entity.auth.RoleEntity;
import com.example.bsumts.repository.RoleRepository;
import com.example.bsumts.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public RoleEntity save(RoleEntity role) {
        RoleEntity savedRole = roleRepository.save(role);
        log.info("Saved - role with id: {} successfully", savedRole.getId());
        return savedRole;
    }

    @Override
    public RoleEntity get(UUID id) {
        RoleEntity role = roleRepository.getById(id);
        log.info("Get - role by id: {}", id);
        return role;
    }

    @Override
    public RoleEntity getByName(String name) {
        RoleEntity role = roleRepository.findByName(name);
        log.info("Get - role by name: {}", name);
        return role;
    }

    @Override
    public List<RoleEntity> getAll() {
        log.info("Get all roles");
        return roleRepository.findAll();
    }

}
