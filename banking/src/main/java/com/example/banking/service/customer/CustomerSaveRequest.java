package com.example.banking.service.customer;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CustomerSaveRequest {

    private String fullName;
    private String phone;
    private String email;
    private String address;
    private String balance;
}
