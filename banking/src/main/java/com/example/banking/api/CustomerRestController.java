package com.example.banking.api;

import com.example.banking.model.Customers;
import com.example.banking.service.CustomerService;
import com.example.banking.service.customer.CustomerSaveRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@AllArgsConstructor
public class CustomerRestController {
    private final CustomerService customerService;

    @GetMapping
    public List<Customers> findAll() {
        return customerService.findAll();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CustomerSaveRequest request) {
        customerService.create(request);
        return ResponseEntity.ok(request);
    }
}
