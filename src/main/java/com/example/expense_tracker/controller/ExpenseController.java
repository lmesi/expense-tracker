package com.example.expense_tracker.controller;

import com.example.expense_tracker.dto.ExpenseDto;
import com.example.expense_tracker.model.Expense;
import com.example.expense_tracker.model.User;
import com.example.expense_tracker.service.ExpenseService;
import com.example.expense_tracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private UserService userService;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    public List<Expense> getAllExpenses(Principal principal) {
        return expenseService.getAllExpensesByUserId(getUserId(principal));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    public Optional<Expense> getExpenseById(@PathVariable long id) {
        return expenseService.getExpenseById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    public Expense createExpense(@RequestBody ExpenseDto expenseDto, Principal principal) {
        return expenseService.createExpense(getUserId(principal), expenseDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    public Expense updateExpense(@PathVariable Long id, @RequestBody ExpenseDto expenseDto) {
        return expenseService.updateExpense(id, expenseDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    public void deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
    }

    private Long getUserId(Principal principal) {
        String username = principal.getName();
        User user = userService.findByUsername(username);
        return user.getId();
    }
}
