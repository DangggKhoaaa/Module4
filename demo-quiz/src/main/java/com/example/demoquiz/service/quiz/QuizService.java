package com.example.demoquiz.service.quiz;

import com.example.demoquiz.model.Question;
import com.example.demoquiz.model.Quiz;
import com.example.demoquiz.model.UserQuiz;
import com.example.demoquiz.repository.QuestionRepository;
import com.example.demoquiz.repository.QuizRepository;
import com.example.demoquiz.repository.UserQuizRepository;
import com.example.demoquiz.service.request.UserQuizSaveRequest;
import com.example.demoquiz.util.AppUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class QuizService {

    private final QuizRepository quizRepository;

    private final UserQuizRepository userQuizRepository;


    public QuizService(QuizRepository quizRepository, UserQuizRepository userQuizRepository) {
        this.quizRepository = quizRepository;
        this.userQuizRepository = userQuizRepository;
    }

    public Page<Quiz> findAll(Pageable pageable) {

        return quizRepository.findAll(pageable);
    }

    public Quiz findById(Long id) {
        return quizRepository.findById(id).get();
    }
    public void saveScore(UserQuizSaveRequest request) {
        UserQuiz newScore = AppUtils.mapper.map(request, UserQuiz.class);
        userQuizRepository.save(newScore);
    }
}
