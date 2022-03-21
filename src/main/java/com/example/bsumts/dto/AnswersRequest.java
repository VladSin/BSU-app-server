package com.example.bsumts.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AnswersRequest {
    private UUID userId;
    private UUID questionId;
    private String answer;
}
