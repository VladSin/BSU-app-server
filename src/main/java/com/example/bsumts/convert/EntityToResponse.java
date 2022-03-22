package com.example.bsumts.convert;

import com.example.bsumts.dto.AnswerResponse;
import com.example.bsumts.dto.QuestionResponse;
import com.example.bsumts.dto.RoleResponse;
import com.example.bsumts.dto.UserResponse;
import com.example.bsumts.entity.auth.RoleEntity;
import com.example.bsumts.entity.auth.UserEntity;
import com.example.bsumts.entity.exam.AnswerEntity;
import com.example.bsumts.entity.exam.QuestionEntity;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class EntityToResponse {

    public UserResponse userToResponse(UserEntity user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setGroupName(user.getGroupName());
        response.setRole(user.getRole().getName());
        if (user.getAnswers() != null) {
            response.setAnswers(user.getAnswers()
                    .stream()
                    .map(this::answerToResponse)
                    .collect(Collectors.toList()));
        }
        return response;
    }

    public AnswerResponse answerToResponse(AnswerEntity answer) {
        AnswerResponse response = new AnswerResponse();
        response.setId(answer.getId().toString());
        response.setAnswer(answer.getAnswer());
        response.setQuestion(answer.getQuestion().getQuestion());
        return response;
    }

    public QuestionResponse questionToResponse(QuestionEntity question) {
        QuestionResponse response = new QuestionResponse();
        response.setId(question.getId().toString());
        response.setQuestion(question.getQuestion());
        return response;
    }

    public RoleResponse roleToResponse(RoleEntity role) {
        RoleResponse response = new RoleResponse();
        response.setId(role.getId().toString());
        response.setName(role.getName());
        return response;
    }

}
