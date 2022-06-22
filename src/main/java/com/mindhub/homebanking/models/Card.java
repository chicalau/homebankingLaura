package com.mindhub.homebanking.models;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="cardHolder")
    private Client cardHolder;

    private LocalDate fromDate;
    private LocalDate thruDate;
    private CardType cardType;
    private CardColor cardColor;
    private String number;

    private boolean isActive;
    private int cvv;

    public Card(){};
    public Card(LocalDate fromDate, LocalDate thruDate, CardType type, CardColor color, String number, int cvv, Client cardHolder) {
        this.cardHolder = cardHolder;
        this.fromDate = fromDate;
        this.thruDate = thruDate;
        this.cardType = type;
        this.cardColor = color;
        this.number = number;
        this.cvv = cvv;
        this.isActive = true;
    }

    public long getId() {
        return id;
    }

    public Client getCardHolder() {
        return cardHolder;
    }
    public void setCardHolder(Client cardHolder) {
        this.cardHolder = cardHolder;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }
    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }
    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }

    public CardType getType() {
        return cardType;
    }
    public void setType(CardType type) {
        this.cardType = type;
    }

    public CardColor getColor() {
        return cardColor;
    }
    public void setColor(CardColor color) {
        this.cardColor = color;
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

    public boolean isActive() {
        return isActive;
    }
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }









}

