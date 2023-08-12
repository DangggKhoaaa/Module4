package com.example.demoquiz.controller.rest;

import com.example.demoquiz.model.Question;
import com.example.demoquiz.repository.QuestionRepository;
import com.example.demoquiz.service.question.QuestionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/question")
public class QuestionRestController {

    private final QuestionService questionService;

    private final QuestionRepository questionRepository;

    public QuestionRestController(QuestionService questionService, QuestionRepository questionRepository) {
        this.questionService = questionService;
        this.questionRepository = questionRepository;
    }

    @GetMapping
    public List<Question> findAll() {
        return questionService.findAll();
    }
    @GetMapping("{id}")
    public Page<Question> findQuestionByQuiz(@PathVariable Long id, @PageableDefault(size = 10)Pageable pageable){
        return questionService.findQuestionByQuiz(id, pageable);
    }
}
