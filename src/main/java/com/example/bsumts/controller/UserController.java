package com.example.bsumts.controller;

import com.example.bsumts.convert.EntityToResponse;
import com.example.bsumts.dto.UserResponse;
import com.example.bsumts.entity.auth.UserEntity;
import com.example.bsumts.service.RoleService;
import com.example.bsumts.service.UserService;
import com.example.bsumts.service.security.SecurityService;
import com.example.bsumts.type.GroupType;
import com.example.bsumts.type.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;
    private final SecurityService securityService;
    private final EntityToResponse entityToResponse;

    @Value("${api.key}")
    private String securityKey;


    @PostMapping("/api/v1/adminId/{adminId}/{firstName}/{lastName}/{groupName}")
    public ResponseEntity<UserResponse> saveUser(@PathVariable(name = "adminId") UUID adminId,
                                                 @PathVariable(name = "firstName") String firstName,
                                                 @PathVariable(name = "lastName") String lastName,
                                                 @PathVariable(name = "groupName") String groupName) {

        if (securityService.checkAdminId(adminId)) {
            UserEntity user = new UserEntity(
                    null, firstName, lastName, groupName, roleService.getByName(RoleType.USER.name()), null);
            userService.save(user);
            return new ResponseEntity<>(entityToResponse.userToResponse(user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

    @PostMapping("/api/v1/security/{securityKey}/admin/{firstName}/{lastName}")
    public ResponseEntity<UserResponse> saveAdmin(@PathVariable(name = "securityKey") String securityKey,
                                                  @PathVariable(name = "firstName") String firstName,
                                                  @PathVariable(name = "lastName") String lastName) {

        if (this.securityKey.equals(securityKey)) {
            UserEntity user = new UserEntity(
                    null, firstName, lastName, null, roleService.getByName(RoleType.ADMIN.name()), null);
            userService.save(user);
            return new ResponseEntity<>(entityToResponse.userToResponse(user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

    @GetMapping("/api/v1/adminId/{adminId}/all")
    public ResponseEntity<List<UserResponse>> getUsers(@PathVariable(name = "adminId") UUID adminId) {

        if (securityService.checkAdminId(adminId)) {
            List<UserEntity> users = userService.getAll();
            List<UserResponse> responses = new ArrayList<>();
            for (UserEntity user : users) {
                responses.add(entityToResponse.userToResponse(user));
            }

            return new ResponseEntity<>(responses, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

    @GetMapping("/api/v1/adminId/{adminId}/all/kb")
    public ResponseEntity<List<UserResponse>> getUsersKB(@PathVariable(name = "adminId") UUID adminId) {

        if (securityService.checkAdminId(adminId)) {
            List<UserEntity> users = userService.getAllByGroup(GroupType.KB.name());
            List<UserResponse> responses = new ArrayList<>();
            for (UserEntity user : users) {
                responses.add(entityToResponse.userToResponse(user));
            }

            return new ResponseEntity<>(responses, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

    @GetMapping("/api/v1/adminId/{adminId}/all/pi")
    public ResponseEntity<List<UserResponse>> getUsersPI(@PathVariable(name = "adminId") UUID adminId) {

        if (securityService.checkAdminId(adminId)) {
            List<UserEntity> users = userService.getAllByGroup(GroupType.PI.name());
            List<UserResponse> responses = new ArrayList<>();
            for (UserEntity user : users) {
                responses.add(entityToResponse.userToResponse(user));
            }

            return new ResponseEntity<>(responses, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

    @GetMapping("/api/v1/adminId/{adminId}/userId/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable(name = "adminId") UUID adminId,
                                                    @PathVariable(name = "userId") UUID userId) {

        if (securityService.checkAdminId(adminId)) {
            try {
                UserEntity user = userService.get(userId);
                return new ResponseEntity<>(entityToResponse.userToResponse(user), HttpStatus.OK);
            } catch (Exception ex) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

    @PutMapping("/api/v1/adminId/{adminId}/userId/{userId}/{firstName}/{lastName}/{groupName}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable(name = "adminId") UUID adminId,
                                                   @PathVariable(name = "userId") UUID userId,
                                                   @PathVariable(name = "firstName") String firstName,
                                                   @PathVariable(name = "lastName") String lastName,
                                                   @PathVariable(name = "groupName") String groupName) {

        if (securityService.checkAdminId(adminId)) {
            UserEntity user = userService.get(userId);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setGroupName(groupName);
            userService.update(user);
            return new ResponseEntity<>(entityToResponse.userToResponse(user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

    @DeleteMapping("/api/v1/adminId/{adminId}/userId/{userId}")
    public ResponseEntity<List<UserEntity>> deleteUser(@PathVariable(name = "adminId") UUID adminId,
                                                       @PathVariable(name = "userId") UUID userId) {

        if (securityService.checkAdminId(adminId)) {
            try {
                userService.delete(userService.get(userId));
                return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
            } catch (Exception ex) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

}
