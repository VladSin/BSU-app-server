package com.example.bsumts.service;

import com.example.bsumts.entity.exam.QuestionEntity;

import java.util.List;
import java.util.UUID;

public interface QuestionService {

    QuestionEntity save(QuestionEntity question);

    QuestionEntity get(UUID id);

    List<QuestionEntity> getAllNotUsedKB();

    List<QuestionEntity> getAllNotUsedPI();

    List<QuestionEntity> getAll();

    void update(QuestionEntity question);

    void updateUsageStatus(QuestionEntity question);

    void updateQuestion(QuestionEntity question);

    void delete(QuestionEntity question);

}
