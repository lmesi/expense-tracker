package com.example.expense_tracker.dto;

import com.example.expense_tracker.model.ExpenseCategory;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ExpenseDto {
    private double amount;
    private ExpenseCategory category;
    private String description;
    private String date;
}
