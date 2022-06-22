package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.models.CardType;

import java.time.LocalDate;

public class CardTransactionDTO {

    private String number;

    //private String cardHolder;

    private int cvv;

    private CardType type;

    private double amount;

    private String description;

    private String cardHolder;

    private LocalDate thruDate;

    public CardTransactionDTO(String number, int cvv, CardType type, double amount, String description, String cardHolder, LocalDate thruDate) {
        this.number = number;
        this.cardHolder = cardHolder;
        this.cvv = cvv;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.thruDate = thruDate;
    }

    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }

    public String getCardHolder() {
       return cardHolder;
    }
    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public int getCvv() {
        return cvv;
    }
    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public CardType getType() {
        return type;
    }
    public void setType(CardType type) {
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

    public LocalDate getThruDate() {
        return thruDate;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }
}
