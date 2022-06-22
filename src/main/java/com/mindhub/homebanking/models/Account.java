package com.mindhub.homebanking.models;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client owner;

    @OneToMany(mappedBy="account", fetch=FetchType.EAGER)
    private Set<Transaction> transactions = new HashSet<>();

    private String number;
    private LocalDate creationDate;
    private double balance;

    private AccountType accountType;
    private boolean isActive;

    public Account (){};

    public Account(String number, LocalDate creationDate, double balance, Client client, AccountType accountType) {
        this.owner = client;
        this.number = number;
        this.creationDate = creationDate;
        this.balance = balance;
        this.isActive = true;
        this.accountType = accountType;
    }
    public long getId() {return id;}

    public String getNumber() {return number;}
    public void setNumber(String number) {this.number = number;}

    public LocalDate getCreationDate() {return creationDate;}
    public void setCreationDate(LocalDate creationDate) {this.creationDate = creationDate;}

    public double getBalance() {return balance;}
    public void setBalance(double balance) {this.balance = balance;}

    public Client getClient() {return owner;}
    public void setClient_id(Client client) {
        this.owner=client;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    } //el set este es el tipo de dato
    public void addTransaction(Transaction transaction) {
        transaction.setAccount_id(this);
        transactions.add(transaction);  //transactions por el nombre que le puse al setttt
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public boolean isActive() {
        return isActive;
    }
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }


}
