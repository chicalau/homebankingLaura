package com.mindhub.homebanking.Controller;

import com.mindhub.homebanking.DTO.LoanApplicationDTO;
import com.mindhub.homebanking.DTO.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.LoanService;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientLoanRepository clientLoanRepository;

    @Autowired
    private LoanService loanService;

    @GetMapping("/loans")
    public List<LoanDTO> getAll(){
        return loanService.getAll();
    }

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> loanCreate(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication) {

        Client clientCurrent = clientService.getClientByEmail(authentication.getName());
        Loan loan = loanService.getById(loanApplicationDTO.getIdLoan());
        Account accountDestino = accountService.getbyNumber(loanApplicationDTO.getNumberAccountDestiny());

        if ( accountDestino == null ) {
            return new ResponseEntity<>("Invalid destiny account", HttpStatus.FORBIDDEN);
        }

        if (!clientCurrent.getAccounts().contains(accountDestino)){
            return new ResponseEntity<>("Destiny account does not belong to the customer", HttpStatus.FORBIDDEN);
        }

        if (loan == null) {
            return new ResponseEntity<>("the loan don´t exists", HttpStatus.FORBIDDEN);
        }

        if(loanApplicationDTO.getAmount() <= 0 ) {
            return new ResponseEntity<>("Invalid amount", HttpStatus.FORBIDDEN);
        }

        if (loanApplicationDTO.getPayments() == 0){
            return new ResponseEntity<>("Invalid payments", HttpStatus.FORBIDDEN);
        }

        if (loan.getMaxAmount() < loanApplicationDTO.getAmount()) {
            return new ResponseEntity<>("Invalid amount, verify loan type", HttpStatus.FORBIDDEN);
        }

        if (!loan.getPayments().contains(loanApplicationDTO.getPayments()) ) {
            return new ResponseEntity<>("Invalid payments, verify loan type", HttpStatus.FORBIDDEN);
        }

        ClientLoan clientLoanAply = new ClientLoan(loanApplicationDTO.getAmount()*(loan.getPercentage()+1), loanApplicationDTO.getPayments(), clientCurrent,loan);
        clientLoanRepository.save(clientLoanAply);

        Transaction transactionLoan = new Transaction(TransactionType.CREDITO,loanApplicationDTO.getAmount(),loan.getName()+" loan approved", LocalDateTime.now(),accountDestino);
        transactionService.saveTransaction(transactionLoan);

        accountDestino.setBalance(accountDestino.getBalance()+loanApplicationDTO.getAmount());
        accountService.saveAccount(accountDestino);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/loans/admin")
    public ResponseEntity<Object> createloan(Authentication authentication, @RequestParam String name, @RequestParam double maxAmount, @RequestParam List<Integer> payments, @RequestParam double percentage){

        Client admin = clientService.getClientByEmail(authentication.getName());
        List<LoanDTO> loans = loanService.getAll();

        if(!admin.getEmail().contains("@admin")){
            return new ResponseEntity<>("Invalid admin", HttpStatus.FORBIDDEN);
        }
        if (name.isEmpty()){
            return new ResponseEntity<>("Missing name", HttpStatus.FORBIDDEN);
        }
        if(loanService.getByName(name) != null){
            return new ResponseEntity<>("name already in use", HttpStatus.FORBIDDEN);
        }
        if (maxAmount <= 0){
            return new ResponseEntity<>("Max amount error", HttpStatus.FORBIDDEN);
        }
        if(percentage <= 0 || percentage > 0.3){
            return new ResponseEntity<>("percentage error", HttpStatus.FORBIDDEN);
        }
        if(payments.size() < 1){
            return new ResponseEntity<>("payments error", HttpStatus.FORBIDDEN);
        }
        if(payments.contains(0)){
            return new ResponseEntity<>("payments error", HttpStatus.FORBIDDEN);
        }

        Loan newLoan = new Loan (name,maxAmount,payments, percentage);
        loanService.saveLoan(newLoan);

        return new ResponseEntity<>("Created",HttpStatus.CREATED);
    }


}
