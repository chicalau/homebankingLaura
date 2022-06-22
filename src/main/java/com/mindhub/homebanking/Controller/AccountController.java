package com.mindhub.homebanking.Controller;

import com.mindhub.homebanking.DTO.AccountDTO;

import com.mindhub.homebanking.Utils.CardUtils;
import com.mindhub.homebanking.models.*;


import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private TransactionService transactionService;


    @GetMapping("/accounts")
    public List<AccountDTO> getAll() {
        return accountService.getListAccounts();
    }

    @GetMapping("/accounts/{id}")
    public AccountDTO getAccount (@PathVariable Long id){
        return accountService.getAccountDTO(id);
    }

    int min =00000000;
    int max =99999999;

    String accountNumber = String.valueOf(CardUtils.getRandomNumber(min, max));

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> accountsRegister (Authentication authentication, @RequestParam AccountType accountType){

        Client clientCurrent = clientService.getClientCurrent(authentication);;
        Set<Account> accounts = clientCurrent.getAccounts();


        if (accounts.size() >= 3 ) {
            return new ResponseEntity<>("Already have 3 accounts created", HttpStatus.FORBIDDEN);
        }

        Account accountCurrent = new Account ("VIN"+ accountNumber,LocalDate.now(),0, clientCurrent,accountType);
        accountService.saveAccount(accountCurrent);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/accounts")
    public ResponseEntity<Object> deleteaccount(Authentication authentication, @RequestParam long id) {

        Client clientCurrent = clientService.getClientCurrent(authentication);;
        Account accountE = accountService.getById(id);
        Set<Transaction> transactions = accountE.getTransactions();

        if (!clientCurrent.getAccounts().contains(accountE)){
            return new ResponseEntity<>("Invalid", HttpStatus.FORBIDDEN);
        }
        if(!accountE.isActive()){
            return new ResponseEntity<>("Invalid", HttpStatus.FORBIDDEN);
        }
        if (accountE.getBalance() > 0){
            return new ResponseEntity<>("can not delete account with amount", HttpStatus.FORBIDDEN);
        }

        accountE.setActive(false);
        accountService.saveAccount(accountE);

        transactions.forEach(transaction -> {
            transaction.setActive(false);
            transactionService.saveTransaction(transaction);
        });

        return new ResponseEntity<>("Created",HttpStatus.CREATED);
    }





}