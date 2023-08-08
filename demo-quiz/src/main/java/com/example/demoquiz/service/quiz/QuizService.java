package com.example.demoquiz.service.quiz;

import com.example.demoquiz.model.Quiz;
import com.example.demoquiz.repository.QuizRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {

    private final QuizRepository quizRepository;

    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public List<Quiz> findAll() {

        return quizRepository.findAll();
    }
}
