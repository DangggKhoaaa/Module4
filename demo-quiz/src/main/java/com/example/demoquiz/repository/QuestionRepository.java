package com.example.demoquiz.repository;

import com.example.demoquiz.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    Page<Question> findByQuizQ_Id(Long quizQ_id, Pageable pageable);
}
