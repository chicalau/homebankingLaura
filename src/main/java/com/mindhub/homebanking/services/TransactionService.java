package com.mindhub.homebanking.services;

import com.mindhub.homebanking.DTO.TransactionDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {


    List<TransactionDTO> getAll();

    void saveTransaction(Transaction transaction);




}
