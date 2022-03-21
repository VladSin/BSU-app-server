package com.example.bsumts.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AnswerResponse {
    private UUID id;
    private String answer;
    private String question;
}
