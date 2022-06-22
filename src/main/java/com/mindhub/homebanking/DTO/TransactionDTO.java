package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;

import java.time.LocalDateTime;

public class TransactionDTO {

    private Long id;
    private TransactionType type;
    private double amount;
    private String description;
    private LocalDateTime date;

    private double amountAccount;

    private boolean isActive;

    public TransactionDTO (Transaction transaction) {
        this.id= transaction.getId();
        this.type = transaction.getType();
        this.amount = transaction.getAmount();
        this.description = transaction.getDescription();
        this.date = transaction.getDate();
        this.amountAccount = transaction.getAmountAccount();
        this.isActive = transaction.isActive();
    }

    public Long getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }
    public void setType(TransactionType type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public double getAmountAccount() {
        return amountAccount;
    }
    public void setAmountAccount(double amountAccount) {
        this.amountAccount = amountAccount;
    }

    public boolean isActive() {
        return isActive;
    }
    public void setActive(boolean active) {
        isActive = active;
    }
}

