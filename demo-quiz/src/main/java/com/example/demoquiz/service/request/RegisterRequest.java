package com.example.demoquiz.service.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
}
