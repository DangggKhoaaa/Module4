package com.example.demoquiz.service.request;

import com.example.demoquiz.model.Quiz;
import com.example.demoquiz.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserQuizSaveRequest {
    private String score;
    private String quiz_id;
}
