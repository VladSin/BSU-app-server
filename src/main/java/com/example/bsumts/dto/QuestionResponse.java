package com.example.bsumts.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class QuestionResponse {
    private UUID id;
    private String question;
}
