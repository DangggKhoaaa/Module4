package com.example.demoquiz.service.answer;

import com.example.demoquiz.model.Answer;
import com.example.demoquiz.repository.AnswerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;

    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public List<Answer> findById(Long id) {
        return answerRepository.findByQuestionId(id);
    }
}
