package com.example.demoquiz.controller;

import com.example.demoquiz.service.answer.AnswerService;
import com.example.demoquiz.service.question.QuestionService;
import com.example.demoquiz.service.quiz.QuizService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/quiz")
public class QuizController {
    private final QuizService quizService;

    private final QuestionService questionService;

    private final AnswerService answerService;

    public QuizController(QuizService quizService, QuestionService questionService, AnswerService answerService) {
        this.quizService = quizService;
        this.questionService = questionService;
        this.answerService = answerService;
    }

    @GetMapping
    public String show() {
        return "quiz";
    }
}
