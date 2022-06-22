package com.mindhub.homebanking.services;

import com.mindhub.homebanking.DTO.LoanDTO;
import com.mindhub.homebanking.models.Loan;

import java.util.List;

public interface LoanService {


    List<LoanDTO> getAll();

    Loan getById(long id);

    Loan getByName(String name);
    void saveLoan (Loan loan);

}
