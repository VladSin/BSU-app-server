package com.example.bsumts.service.impl;

import com.example.bsumts.entity.auth.UserEntity;
import com.example.bsumts.repository.UserRepository;
import com.example.bsumts.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Override
    public UserEntity save(UserEntity user) {
        user.setCreated(new Date());
        user.setUpdated(new Date());
        UserEntity savedUser = userRepository.save(user);
        log.info("Saved - user with id: {} successfully registered", savedUser.getId());
        return savedUser;
    }

    @Override
    public UserEntity get(UUID id) {
        UserEntity user = userRepository.getById(id);
        log.info("Get - user by id: {}", id);
        return user;
    }

    @Override
    public List<UserEntity> getAll() {
        log.info("Get all users");
        return userRepository.findAll();
    }

    @Override
    public List<UserEntity> getAllByGroup(String groupName) {
        log.info("Get all users by group: {}", groupName);
        return userRepository.findAllByGroupName(groupName);
    }

    @Override
    public void update(UserEntity user) {
        log.info("Updated - user with id: {}", user.getId());
        userRepository.updateAllData(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getGroupName(),
                user.getRole(),
                user.getAnswers(),
                user.getUpdated());
    }

    @Override
    public void delete(UserEntity user) {
        log.info("Deleted - user with id: {}", user.getId());
        userRepository.delete(user);
    }

}
