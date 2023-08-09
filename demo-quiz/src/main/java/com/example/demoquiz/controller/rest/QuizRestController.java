package com.example.demoquiz.controller.rest;

import com.example.demoquiz.model.Quiz;
import com.example.demoquiz.repository.QuizRepository;
import com.example.demoquiz.service.quiz.QuizService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("{id}")
    public Page<Quiz> findAll(@PageableDefault(size = 9) Pageable pageable, @PathVariable Long id) {
        return quizService.findAll(pageable);
    }
}
