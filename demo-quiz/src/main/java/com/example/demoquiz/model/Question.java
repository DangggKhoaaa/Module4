package com.example.demoquiz.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private String type;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    @JsonIgnore
    private Quiz quizQ;

    @OneToMany(mappedBy = "question")
    private List<Answer> answers;

}
