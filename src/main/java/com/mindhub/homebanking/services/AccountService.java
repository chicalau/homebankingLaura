package com.mindhub.homebanking.services;

import com.mindhub.homebanking.DTO.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;

import java.util.List;

public interface AccountService {

    List<AccountDTO> getListAccounts();

    AccountDTO getAccountDTO(long id);

    Account getById(long id);

    void saveAccount(Account account);

    Account getbyNumber(String number);



}
