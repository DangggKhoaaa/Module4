package com.example.demoquiz.controller.rest;

import com.example.demoquiz.repository.AnswerRepository;
import com.example.demoquiz.service.answer.AnswerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/answer")
public class AnswerRestController {

    private final AnswerService answerService;

    private final AnswerRepository answerRepository;

    public AnswerRestController(AnswerService answerService, AnswerRepository answerRepository) {
        this.answerService = answerService;
        this.answerRepository = answerRepository;
    }

}
