package com.example.expense_tracker.service;

import com.example.expense_tracker.dto.ExpenseDto;
import com.example.expense_tracker.model.Expense;
import com.example.expense_tracker.model.User;
import com.example.expense_tracker.repository.ExpenseRepository;
import com.example.expense_tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Expense> getAllExpensesByUserId(Long userId) {
        return expenseRepository.findByUserId(userId);
    }

    public Optional<Expense> getExpenseById(Long id) {
        return expenseRepository.findById(id);
    }

    public Expense createExpense(Long userId, ExpenseDto expenseDto) {
        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            Expense expense = new Expense();
            expense.setAmount(expenseDto.getAmount());
            expense.setCategory(expenseDto.getCategory());
            expense.setDescription(expenseDto.getDescription());
            expense.setDate(formatStringDate(expenseDto.getDate()));
            expense.setUser(user.get());
            return expenseRepository.save(expense);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public Expense updateExpense(Long id, ExpenseDto expenseDto) {
        Optional<Expense> expenseOptional = expenseRepository.findById(id);

        if (expenseOptional.isPresent()) {
            Expense expense = expenseOptional.get();
            expense.setAmount(expenseDto.getAmount());
            expense.setCategory(expenseDto.getCategory());
            expense.setDate(formatStringDate(expenseDto.getDate()));
            expense.setDescription(expenseDto.getDescription());
            return expenseRepository.save(expense);
        } else {
            throw new RuntimeException("Expense not found");
        }
    }

    public void deleteExpense(Long id) {
        if (expenseRepository.existsById(id)) {
            expenseRepository.deleteById(id);
        } else {
            throw new RuntimeException("Expense not found");
        }
    }

    private LocalDate formatStringDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, formatter);
    }
}
