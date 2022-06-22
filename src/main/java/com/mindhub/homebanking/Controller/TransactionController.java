package com.mindhub.homebanking.Controller;

import com.mindhub.homebanking.DTO.TransactionDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping("/transactions")
    public List<TransactionDTO> getAll(@RequestParam (required = false) @DateTimeFormat (pattern = "yyyy-MM-dd") LocalDate from,
                                       @RequestParam (required = false) @DateTimeFormat (pattern = "yyyy-MM-dd")  LocalDate to) {

        if(from != null && to != null ){
            return transactionRepository.findByDateBetween(from.atTime(0,0,0), to.atTime(23,59,50)).stream().map(TransactionDTO::new).collect(toList());

        }else {
            return transactionService.getAll();
        }
    }


    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<Object> transactionCreate (Authentication authentication, @RequestParam double amount, @RequestParam String description,@RequestParam String accountOrigen,@RequestParam String accountDestino){

        Client clientCurrent = clientService.getClientByEmail(authentication.getName());
        Account origen = accountService.getbyNumber(accountOrigen);
        Account destino = accountService.getbyNumber(accountDestino);

        if (accountOrigen.isEmpty()) {
            return new ResponseEntity<>("Missing origin account", HttpStatus.FORBIDDEN);
        }

        if (accountDestino.isEmpty()){
            return new ResponseEntity<>("Missing destiny account", HttpStatus.FORBIDDEN);
        }

        if  (description.isEmpty()){
            return new ResponseEntity<>("Missing description", HttpStatus.FORBIDDEN);
        }

        if (origen == destino){
            return new ResponseEntity<>("Origin account and destiny account are the same", HttpStatus.FORBIDDEN);
        }

        if (origen == null || destino == null) {
            return new ResponseEntity<>("the account don´t exists", HttpStatus.FORBIDDEN);
        }

        if (!clientCurrent.getAccounts().contains(origen)) {
            return new ResponseEntity<>("Missing account", HttpStatus.FORBIDDEN);
        }

        if(amount <= 0){
            return new ResponseEntity<>("Invalid amount", HttpStatus.FORBIDDEN);
        }

        if (origen.getBalance() < amount) {
            return new ResponseEntity<>("not have enough amount", HttpStatus.FORBIDDEN);
        }

        Transaction transactionCurrentOrigen = new Transaction(TransactionType.DEBITO,-amount,description+" - "+destino.getNumber(), LocalDateTime.now(), origen);
        transactionService.saveTransaction(transactionCurrentOrigen);

        Transaction transactionDestino = new Transaction(TransactionType.CREDITO,amount,description+" - "+origen.getNumber(),LocalDateTime.now(),destino);
        transactionService.saveTransaction(transactionDestino);

        origen.setBalance(origen.getBalance()- amount);
        accountService.saveAccount(origen);
        destino.setBalance(destino.getBalance()+ amount);
        accountService.saveAccount(destino);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }



}
