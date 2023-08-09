package com.example.demoquiz.service.question;

import com.example.demoquiz.model.Question;
import com.example.demoquiz.repository.QuestionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<Question> findById(Long id) {
//        return questionRepository.findByQuizQId(id);
        return null;
    }
    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    public Page<Question> findQuestionByQuiz(Long id, Pageable pageable) {
        return questionRepository.findByQuizQ_Id(id, pageable);
    }
}
