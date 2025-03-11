package com.example.demo.service;

import com.example.demo.dto.ExpenseDTO;
import com.example.demo.entity.Expense;
import com.example.demo.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    // Fetch all expenses
    public List<ExpenseDTO> getAllExpenses() {
        List<Expense> expenses = expenseRepository.findAll();
        return expenses.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Add a new expense
    public ExpenseDTO addExpense(ExpenseDTO expenseDTO) {
        Expense expense = convertToEntity(expenseDTO);

        // Ensure the date is set
        if (expense.getDate() == null) {
            expense.setDate(LocalDate.now());
        }

        Expense savedExpense = expenseRepository.save(expense);
        return convertToDTO(savedExpense);
    }

    // Update an existing expense (PUT - full update)
    public ExpenseDTO updateExpense(Long id, ExpenseDTO expenseDTO) {
        Expense existingExpense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found with id: " + id));

        existingExpense.setName(expenseDTO.getName());
        existingExpense.setAmount(expenseDTO.getAmount());
        existingExpense.setCategory(expenseDTO.getCategory());
        existingExpense.setDate(expenseDTO.getDate());

        Expense updatedExpense = expenseRepository.save(existingExpense);
        return convertToDTO(updatedExpense);
    }

    // Partially update an expense (PATCH)
    public ExpenseDTO partiallyUpdateExpense(Long id, Map<String, Object> updates) {
        Expense existingExpense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found with id: " + id));

        // Apply updates selectively
        updates.forEach((key, value) -> {
            switch (key) {
                case "name":
                    existingExpense.setName((String) value);
                    break;
                case "amount":
                    existingExpense.setAmount(Double.valueOf(value.toString()));
                    break;
                case "category":
                    existingExpense.setCategory((String) value);
                    break;
                case "date":
                    existingExpense.setDate(LocalDate.parse(value.toString()));
                    break;
                default:
                    throw new IllegalArgumentException("Invalid field: " + key);
            }
        });

        Expense updatedExpense = expenseRepository.save(existingExpense);
        return convertToDTO(updatedExpense);
    }

    // Delete an expense
    public void deleteExpense(Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found with id: " + id));
        expenseRepository.delete(expense);
    }

    // Helper methods to convert between Expense and ExpenseDTO
    private ExpenseDTO convertToDTO(Expense expense) {
        return new ExpenseDTO(expense.getName(), expense.getAmount(), expense.getCategory(), expense.getDate());
    }

    private Expense convertToEntity(ExpenseDTO expenseDTO) {
        Expense expense = new Expense(expenseDTO.getName(), expenseDTO.getAmount(), expenseDTO.getCategory());

        // Set the date if it's not null, otherwise use the current date
        expense.setDate(expenseDTO.getDate() != null ? expenseDTO.getDate() : LocalDate.now());

        return expense;
    }
}
