package com.example.demo.dto;

import java.time.LocalDate;

public class ExpenseDTO {
    private String name;
    private double amount;
    private String category;
    private LocalDate date;

    // Constructors
    public ExpenseDTO() {
    }

    public ExpenseDTO(String name, double amount, String category, LocalDate date) {
        this.name = name;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
