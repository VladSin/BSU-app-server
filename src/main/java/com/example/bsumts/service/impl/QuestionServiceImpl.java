package com.example.bsumts.service.impl;

import com.example.bsumts.entity.exam.QuestionEntity;
import com.example.bsumts.repository.QuestionRepository;
import com.example.bsumts.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;


    @Override
    public synchronized QuestionEntity save(QuestionEntity question) {
        QuestionEntity savedQuestion = questionRepository.save(question);
        log.info("Saved - question: {} successfully", savedQuestion);
        return savedQuestion;
    }

    @Override
    public synchronized QuestionEntity get(UUID id) {
        QuestionEntity question = questionRepository.getById(id);
        log.info("Get - question: {}, by id: {}", question,  id);
        return question;
    }

    @Override
    public synchronized List<QuestionEntity> getAllNotUsedKB() {
        return questionRepository.findAllByUsedKB(false);
    }

    @Override
    public synchronized List<QuestionEntity> getAllNotUsedPI() {
        return questionRepository.findAllByUsedPI(false);
    }

    @Override
    public synchronized List<QuestionEntity> getAll() {
        log.info("Get all questions");
        return questionRepository.findAll();
    }

    @Override
    public synchronized void update(QuestionEntity question) {
        log.info("Updated - all data for question: {}", question);
        questionRepository.updateAllData(
                question.getId(),
                question.getQuestion(),
                question.getUsedKB(),
                question.getUsedPI(),
                question.getAnswers());
    }

    @Override
    public synchronized void updateUsageStatus(QuestionEntity question) {
        log.info("Updated - question status by id: {}", question.getId());
        questionRepository.updateUsageStatus(question.getId(), question.getUsedKB(), question.getUsedPI());
    }

    @Override
    public synchronized void updateQuestion(QuestionEntity question) {
        log.info("Updated - question: {}, by id: {}", question.getQuestion(), question.getId());
        questionRepository.updateQuestion(question.getId(), question.getQuestion());
    }

    @Override
    public synchronized void delete(QuestionEntity question) {
        log.info("Deleted - question: {}", question);
        questionRepository.delete(question);
    }
}
