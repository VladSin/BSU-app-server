package com.example.bsumts.controller;

import com.example.bsumts.convert.EntityToResponse;
import com.example.bsumts.dto.QuestionResponse;
import com.example.bsumts.entity.exam.QuestionEntity;
import com.example.bsumts.service.QuestionService;
import com.example.bsumts.service.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/question")
public class QuestionController {

    private final QuestionService questionService;
    private final SecurityService securityService;
    private final EntityToResponse entityToResponse;


    @PostMapping("/api/v1/adminId/{adminId}")
    public ResponseEntity<QuestionResponse> save(@PathVariable(name = "adminId") UUID adminId,
                                                 @RequestBody String question) {

        if (securityService.checkAdminId(adminId)) {
            QuestionEntity savedQuestion = questionService.save(
                    new QuestionEntity(null, question, false, false, null));
            return new ResponseEntity<>(entityToResponse.questionToResponse(savedQuestion), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

    @GetMapping("/api/v1/adminId/{adminId}/{questionId}")
    public ResponseEntity<QuestionResponse> getById(@PathVariable(name = "adminId") UUID adminId,
                                                    @PathVariable(name = "questionId") UUID questionId) {

        if (securityService.checkAdminId(adminId)) {
            QuestionEntity question = questionService.get(questionId);
            return new ResponseEntity<>(entityToResponse.questionToResponse(question), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

    @GetMapping("/api/v1/adminId/{adminId}/all")
    public ResponseEntity<List<QuestionResponse>> getAll(@PathVariable(name = "adminId") UUID adminId) {

        if (securityService.checkAdminId(adminId)) {
            List<QuestionResponse> responses = new ArrayList<>();
            for (QuestionEntity question : questionService.getAll()) {
                responses.add(entityToResponse.questionToResponse(question));
            }
            return new ResponseEntity<>(responses, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

    @PutMapping("/api/v1/adminId/{adminId}/{questionId}")
    public ResponseEntity<List<QuestionResponse>> updateById(@PathVariable(name = "adminId") UUID adminId,
                                                             @PathVariable(name = "questionId") UUID questionId,
                                                             @RequestBody String question) {

        if (securityService.checkAdminId(adminId)) {
            QuestionEntity updateQuestion = questionService.get(questionId);
            updateQuestion.setQuestion(question);
            questionService.updateQuestion(updateQuestion);

            List<QuestionResponse> responses = new ArrayList<>();
            for (QuestionEntity q : questionService.getAll()) {
                responses.add(entityToResponse.questionToResponse(q));
            }
            return new ResponseEntity<>(responses, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

    @DeleteMapping("/api/v1/adminId/{adminId}/{questionId}")
    public ResponseEntity<List<QuestionResponse>> deleteById(@PathVariable(name = "adminId") UUID adminId,
                                                             @PathVariable(name = "questionId") UUID questionId) {
        if (securityService.checkAdminId(adminId)) {
            try {
                questionService.delete(questionService.get(questionId));

                List<QuestionResponse> responses = new ArrayList<>();
                for (QuestionEntity q : questionService.getAll()) {
                    responses.add(entityToResponse.questionToResponse(q));
                }
                return new ResponseEntity<>(responses, HttpStatus.OK);
            } catch (Exception ex) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

}
