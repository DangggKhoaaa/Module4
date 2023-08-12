package com.example.demoquiz.service.question;

import com.example.demoquiz.model.Question;
import com.example.demoquiz.repository.QuestionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    public Page<Question> findQuestionByQuiz(Long id, Pageable pageable) {
        List<Question> questions = questionRepository.findByQuizQ_Id(id);

        Collections.shuffle(questions);

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), questions.size());
        List<Question> pageQuestions = questions.subList(start, end);
        Page<Question> page = new PageImpl<>(pageQuestions, pageable, questions.size());

        return page;
    }
}
