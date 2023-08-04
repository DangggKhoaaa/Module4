package com.example.createdashboards.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class Customer {
    @GetMapping("/customer")
    public String showCustomer() {
        return "customer/List";
    }

    @GetMapping("/customer/create")
    public String showCreateCustomer() {
        return "customer/Create";
    }
}
