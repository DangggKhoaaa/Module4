package com.example.createdashboards.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Product {
    @GetMapping("/product")
    public String showProducts() {
        return "product/List";
    }

    @GetMapping("/product/create")
    public String showCreateProducts() {
        return "product/Create";
    }
}
