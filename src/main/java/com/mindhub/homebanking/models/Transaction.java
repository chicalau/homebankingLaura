package com.mindhub.homebanking.models;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="account_id")
    private Account account;

    private TransactionType type;
    private double amount;
    private String description;
    private LocalDateTime date;

    private double amountAccount;

    private boolean isActive;


    public Transaction() {};

    public Transaction(TransactionType type, double amount, String description, LocalDateTime date, Account account_id ){
        this.type= type;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.account = account_id;
        this.amountAccount = account_id.getBalance()+amount;
        this.isActive = true;
    };


    public long getId() {
        return id;
    }

    public Account getAccount_id() {
        return account;
    }
    public void setAccount_id(Account account_id) {
        this.account = account_id;
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

    public boolean isActive() {
        return isActive;
    }
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
}
