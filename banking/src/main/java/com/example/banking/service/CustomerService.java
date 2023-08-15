package com.example.banking.service;

import com.example.banking.model.Customers;
import com.example.banking.repository.CustomerRepository;
import com.example.banking.service.customer.CustomerSaveRequest;
import com.example.banking.util.AppUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;


    public List<Customers> findAll() {
        return customerRepository.findAll();
    }

    public void create(CustomerSaveRequest request) {
        Customers newCustomer = AppUtils.mapper.map(request, Customers.class);
        customerRepository.save(newCustomer);
    }
}
