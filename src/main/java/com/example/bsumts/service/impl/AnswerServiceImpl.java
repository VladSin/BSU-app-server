package com.example.bsumts.service.impl;

import com.example.bsumts.entity.exam.AnswerEntity;
import com.example.bsumts.repository.AnswerRepository;
import com.example.bsumts.service.AnswerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;


    @Override
    public AnswerEntity save(AnswerEntity answer) {
        AnswerEntity savedAnswer = answerRepository.save(answer);
        log.info("Saved - answer: {} successfully", savedAnswer);
        return savedAnswer;
    }

    @Override
    public AnswerEntity get(UUID id) {
        AnswerEntity answer = answerRepository.getById(id);
        log.info("Get - answer: {}, by id: {}", answer,  id);
        return answer;
    }

    @Override
    public List<AnswerEntity> getAll() {
        log.info("Get all answers");
        return answerRepository.findAll();
    }

    @Override
    public void update(AnswerEntity answer) {
        log.info("Updated - all data for answer: {}", answer);
        answerRepository.updateAllData(
                answer.getId(),
                answer.getAnswer(),
                answer.getUser(),
                answer.getQuestion()
        );
    }

    @Override
    public void updateAnswer(AnswerEntity answer) {
        log.info("Updated - only answer: {}, by id: {}", answer.getAnswer(), answer.getId());
        answerRepository.updateAnswer(answer.getId(), answer.getAnswer());
    }

    @Override
    public void delete(AnswerEntity answer) {
        log.info("Deleted - answer: {}", answer);
        answerRepository.delete(answer);
    }

}
