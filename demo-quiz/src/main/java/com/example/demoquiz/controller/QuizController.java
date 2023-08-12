package com.example.demoquiz.controller;

import com.example.demoquiz.model.UserQuiz;
import com.example.demoquiz.repository.UserQuizRepository;
import com.example.demoquiz.service.answer.AnswerService;
import com.example.demoquiz.service.question.QuestionService;
import com.example.demoquiz.service.quiz.QuizService;
import com.example.demoquiz.service.request.UserQuizSaveRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/quiz")
public class QuizController {
    private final QuizService quizService;

    private final QuestionService questionService;

    private final AnswerService answerService;


    public QuizController(QuizService quizService, QuestionService questionService, AnswerService answerService, UserQuizRepository userQuizRepository) {
        this.quizService = quizService;
        this.questionService = questionService;
        this.answerService = answerService;
    }

    @GetMapping
    public String show() {
        return "quiz";
    }

    @PostMapping("/save-score")
    public ResponseEntity<?> saveScore(@RequestBody UserQuizSaveRequest scoreData) {
        quizService.saveScore(scoreData);
        return ResponseEntity.ok(scoreData);
    }
}
