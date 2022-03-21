package com.example.bsumts.service;

import com.example.bsumts.entity.exam.AnswerEntity;

import java.util.List;
import java.util.UUID;

public interface AnswerService {

    AnswerEntity save(AnswerEntity answer);

    AnswerEntity get(UUID id);

    List<AnswerEntity> getAll();

    void update(AnswerEntity answer);

    void updateAnswer(AnswerEntity answer);

    void delete(AnswerEntity answer);

}
