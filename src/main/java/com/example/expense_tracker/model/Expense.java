package com.example.expense_tracker.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Double amount;
    private String category;
    private String description;
    private LocalDate date;

    /*@ManyToOne
    @JoinColumn(name = "user_id")
    private User user;*/

}
