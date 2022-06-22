package com.mindhub.homebanking.DTO;


public class LoanApplicationDTO {

    private long idLoan;

    private double amount;

    private Integer payments;

    private String numberAccountDestiny;

    public LoanApplicationDTO (long idLoan, double amount, Integer payments, String numberAccountDestiny){
        this.idLoan = idLoan;
        this.amount = amount;
        this.payments = payments;
        this.numberAccountDestiny = numberAccountDestiny;
    }


    public long getIdLoan() {
        return idLoan;
    }
    public void setIdLoan(long idLoan) {
        this.idLoan = idLoan;
    }

    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Integer getPayments() {
        return payments;
    }
    public void setPayments(Integer payments) {
        this.payments = payments;
    }

    public String getNumberAccountDestiny() {
        return numberAccountDestiny;
    }
    public void setNumberAccountDestiny(String numberAccountDestiny) {
        this.numberAccountDestiny = numberAccountDestiny;
    }






}