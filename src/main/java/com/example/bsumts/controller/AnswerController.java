package com.example.bsumts.controller;

import com.example.bsumts.convert.EntityToResponse;
import com.example.bsumts.dto.AnswerResponse;
import com.example.bsumts.entity.exam.AnswerEntity;
import com.example.bsumts.service.AnswerService;
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
@RequestMapping(value = "/answer")
public class AnswerController {

    private final AnswerService answerService;
    private final SecurityService securityService;
    private final EntityToResponse entityToResponse;


    @GetMapping("/api/v1/adminId/{adminId}/{answerId}")
    public ResponseEntity<AnswerResponse> getById(@PathVariable(name = "adminId") UUID adminId,
                                                  @PathVariable(name = "answerId") UUID answerId) {

        if (securityService.checkAdminId(adminId)) {
            AnswerEntity answer = answerService.get(answerId);
            return new ResponseEntity<>(entityToResponse.answerToResponse(answer), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

    @GetMapping("/api/v1/adminId/{adminId}")
    public ResponseEntity<List<AnswerResponse>> getAll(@PathVariable(name = "adminId") UUID adminId) {

        if (securityService.checkAdminId(adminId)) {
            List<AnswerResponse> responses = new ArrayList<>();
            for (AnswerEntity answer : answerService.getAll()) {
                responses.add(entityToResponse.answerToResponse(answer));
            }
            return new ResponseEntity<>(responses, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

    @DeleteMapping("/api/v1/adminId/{adminId}/{answerId}")
    public ResponseEntity<List<AnswerResponse>> deleteById(@PathVariable(name = "adminId") UUID adminId,
                                                           @PathVariable(name = "answerId") UUID answerId) {
        if (securityService.checkAdminId(adminId)) {
            try {
                answerService.delete(answerService.get(answerId));
                List<AnswerResponse> responses = new ArrayList<>();
                for (AnswerEntity answer : answerService.getAll()) {
                    responses.add(entityToResponse.answerToResponse(answer));
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
