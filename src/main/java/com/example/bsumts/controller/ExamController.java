package com.example.bsumts.controller;

import com.example.bsumts.convert.EntityToResponse;
import com.example.bsumts.dto.AnswersRequest;
import com.example.bsumts.dto.QuestionResponse;
import com.example.bsumts.dto.RegistrationRequest;
import com.example.bsumts.entity.auth.UserEntity;
import com.example.bsumts.entity.exam.AnswerEntity;
import com.example.bsumts.entity.exam.QuestionEntity;
import com.example.bsumts.service.AnswerService;
import com.example.bsumts.service.QuestionService;
import com.example.bsumts.service.RoleService;
import com.example.bsumts.service.UserService;
import com.example.bsumts.type.GroupType;
import com.example.bsumts.type.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/exam")
public class ExamController {

    private final UserService userService;
    private final RoleService roleService;
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final EntityToResponse entityToResponse;


    @PostMapping("/api/registration")
    public ResponseEntity<String> registration(@RequestBody RegistrationRequest request) {
        if (request != null) {
            UserEntity user = new UserEntity();
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setGroupName(request.getGroupName());
            user.setRole(roleService.getByName(RoleType.USER.name()));
            UserEntity savedUser = userService.save(user);
            return new ResponseEntity<>(savedUser.getId().toString(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    // TODO Быстрый костыль! Заменить метод!
    @GetMapping("/api/questions/userId/{userId}")
    public ResponseEntity<List<QuestionResponse>> getQuestions(@PathVariable(name = "userId") UUID userId) {
        UserEntity user = userService.get(userId);

        List<QuestionEntity> questionsForResponse = new ArrayList<>();
        if (user.getGroupName().equals(GroupType.KB.name())) {
            List<QuestionEntity> questions = questionService.getAllNotUsedKB();
            if (questions.size() >= 2) {
                for (int i = 0; i < 2; i++) {
                    QuestionEntity question = questions.get(i);
                    question.setUsedKB(true);
                    questionService.updateUsageStatus(question);
                    questionsForResponse.add(question);
                }
            } else {
                List<QuestionEntity> allQuestions = questionService.getAll();
                for (int i = 0; i < 2; i++) {
                    QuestionEntity question = allQuestions.get(i);
                    questionsForResponse.add(question);
                }
            }

        } else {
            List<QuestionEntity> questions = questionService.getAllNotUsedPI();
            if (questions.size() >= 2) {
                for (int i = 0; i < 2; i++) {
                    QuestionEntity question = questions.get(i);
                    question.setUsedPI(true);
                    questionService.updateUsageStatus(question);
                    questionsForResponse.add(question);
                }
            } else {
                List<QuestionEntity> allQuestions = questionService.getAll();
                for (int i = 0; i < 2; i++) {
                    QuestionEntity question = allQuestions.get(i);
                    questionsForResponse.add(question);
                }
            }

        }

        List<QuestionResponse> responses = new ArrayList<>();
        for (QuestionEntity question : questionsForResponse) {
            responses.add(entityToResponse.questionToResponse(question));
        }

        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @PostMapping("/api/answers/userId/{userId}")
    public void sendAnswer(@PathVariable(name = "userId") UUID userId,
                           @RequestBody List<AnswersRequest> request) {

        UserEntity user = userService.get(userId);
        for (AnswersRequest answer : request) {
            AnswerEntity newAnswer = new AnswerEntity(
                    null,
                    answer.getAnswer(),
                    user,
                    questionService.get(answer.getQuestionId())
            );
            answerService.save(newAnswer);
        }

    }

}
