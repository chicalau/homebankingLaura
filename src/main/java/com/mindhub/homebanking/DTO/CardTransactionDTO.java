package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.models.CardType;

import java.time.LocalDate;

public class CardTransactionDTO {

    private String number;

    //private String cardHolder;

    private int cvv;


    private double amount;

    private String description;


    public CardTransactionDTO(String number, int cvv,  double amount, String description) {
        this.number = number;
        this.cvv = cvv;
        this.amount = amount;
        this.description = description;
    }

    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }

    public int getCvv() {
        return cvv;
    }
    public void setCvv(int cvv) {
        this.cvv = cvv;
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


}
