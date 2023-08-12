package com.example.demoquiz.controller.rest;

import com.example.demoquiz.model.Quiz;
import com.example.demoquiz.model.User;
import com.example.demoquiz.model.UserQuiz;
import com.example.demoquiz.repository.UserQuizRepository;
import com.example.demoquiz.repository.UserRepository;
import com.example.demoquiz.service.quiz.QuizService;
import com.example.demoquiz.service.request.UserQuizSaveRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/save-score")
public class SaveScoreRestController {
    private final UserRepository userRepository;

    private final QuizService quizService;
    private final UserQuizRepository userQuizRepository;

    public SaveScoreRestController(UserRepository userRepository, QuizService quizService, UserQuizRepository userQuizRepository) {
        this.userRepository = userRepository;
        this.quizService = quizService;
        this.userQuizRepository = userQuizRepository;
    }

    @PostMapping
    public ResponseEntity<?> saveScore(@RequestBody UserQuizSaveRequest scoreData) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findAllByEmailIgnoreCase(email).get();


        UserQuiz userQuiz = new UserQuiz();
        userQuiz.setScore(Integer.valueOf(scoreData.getScore()));
        userQuiz.setDate(LocalDate.now());

        userQuiz.setUser(user);

        String quiz_id = scoreData.getQuiz_id();
        Quiz quiz = quizService.findById(Long.valueOf(quiz_id));
        userQuiz.setQuiz(quiz);

        userQuizRepository.save(userQuiz);

        return ResponseEntity.ok(scoreData);
    }
}
