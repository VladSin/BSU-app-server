package com.example.bsumts.service.security;

import com.example.bsumts.entity.auth.UserEntity;
import com.example.bsumts.service.RoleService;
import com.example.bsumts.service.UserService;
import com.example.bsumts.type.RoleType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SecurityService {

    private final UserService userService;
    private final RoleService roleService;


    public boolean checkAdminId(UUID id) {
        try {
            UserEntity user = userService.get(id);
            return user.getRole().getName().equals(RoleType.ADMIN.name());
        } catch (Exception ex) {
            log.error("Error message: {}", ex.getMessage());
            return false;
        }
    }
}
