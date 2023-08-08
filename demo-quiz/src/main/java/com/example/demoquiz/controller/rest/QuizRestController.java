package com.example.demoquiz.controller.rest;

import com.example.demoquiz.model.Quiz;
import com.example.demoquiz.repository.QuizRepository;
import com.example.demoquiz.service.quiz.QuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/quiz")
public class QuizRestController {

    private final QuizService quizService;

    private final QuizRepository quizRepository;

    public QuizRestController(QuizService quizService, QuizRepository quizRepository) {
        this.quizService = quizService;
        this.quizRepository = quizRepository;
    }

    @GetMapping
    public List<Quiz> findAll() {
        return quizService.findAll();
    }
}
