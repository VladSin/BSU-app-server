package com.example.bsumts.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class UserResponse {
    private UUID id;
    private String firstName;
    private String lastName;
    private String groupName;
    private String role;
    private List<AnswerResponse> answers;
}
