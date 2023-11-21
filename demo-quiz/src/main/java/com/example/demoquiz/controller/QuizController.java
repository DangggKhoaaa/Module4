package com.example.demoquiz.controller;

import com.example.demoquiz.model.User;
import com.example.demoquiz.model.UserQuiz;
import com.example.demoquiz.repository.UserQuizRepository;
import com.example.demoquiz.repository.UserRepository;
import com.example.demoquiz.service.answer.AnswerService;
import com.example.demoquiz.service.question.QuestionService;
import com.example.demoquiz.service.quiz.QuizService;
import com.example.demoquiz.service.request.UserQuizSaveRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/quiz")
public class QuizController {
    private final QuizService quizService;

    private final UserRepository userRepository;


    @GetMapping("{id}")
    public String show(@PathVariable Long id, Authentication authentication, Model model) {
        User user = userRepository.findAllByEmailIgnoreCase(authentication.getName()).get();
        model.addAttribute("user", user);
        quizService.findById(id);
        return "quiz";
    }

    @PostMapping("/save-score")
    public ResponseEntity<?> saveScore(@RequestBody UserQuizSaveRequest scoreData) {
        quizService.saveScore(scoreData);
        return ResponseEntity.ok(scoreData);
    }
}
