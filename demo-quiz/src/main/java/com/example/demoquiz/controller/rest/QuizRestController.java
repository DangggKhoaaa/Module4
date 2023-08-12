package com.example.demoquiz.controller.rest;

import com.example.demoquiz.model.Quiz;
import com.example.demoquiz.repository.QuizRepository;
import com.example.demoquiz.service.quiz.QuizService;
import com.example.demoquiz.service.request.UserQuizSaveRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public Page<Quiz> findAll(@PageableDefault(size = 10) Pageable pageable) {
        return quizService.findAll(pageable);
    }

}
