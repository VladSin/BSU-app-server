package com.example.bsumts.service;

import com.example.bsumts.entity.auth.UserEntity;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserEntity save(UserEntity user);

    UserEntity get(UUID id);

    List<UserEntity> getAll();

    List<UserEntity> getAllByGroup(String groupName);

    void update(UserEntity user);

    void delete(UserEntity user);

}
