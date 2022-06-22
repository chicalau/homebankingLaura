package com.mindhub.homebanking.Controller;

import com.mindhub.homebanking.DTO.CardTransactionDTO;
import com.mindhub.homebanking.Utils.CardUtils;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    CardService cardService;

    @Autowired
    ClientService clientService;

    @Autowired
    TransactionService transactionService;

    @Autowired
    AccountService accountService;

    String cardNumber = CardUtils.getCardNumber();

    int cvv = CardUtils.getCvv();

    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> cardCreate (Authentication authentication, @RequestParam CardType cardType, @RequestParam CardColor cardColor){

        Client clientCurrent = clientService.getClientCurrent(authentication);
        Set<Card> cards = clientCurrent.getCards().stream().filter(card -> card.isActive()).collect(toSet());

        Set<Card> cardsDebito = cards.stream().filter(card -> card.getType() == CardType.DEBITO).collect(toSet());
        Set<Card> cardsCredito = cards.stream().filter(card -> card.getType() == CardType.CREDITO).collect(toSet());

        CardType debito = CardType.DEBITO;
        CardType credito = CardType.CREDITO;

        if (cardType == debito && cardsDebito.size() < 3) {
            Card cardCurrent = new Card (LocalDate.now(), LocalDate.now().plusYears(5),cardType,cardColor,cardNumber,cvv,clientCurrent);
            cardService.saveCard(cardCurrent);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }

        if (cardType == credito && cardsCredito.size() < 3 ){
            Card cardCurrent = new Card (LocalDate.now(), LocalDate.now().plusYears(5),cardType,cardColor,cardNumber,cvv,clientCurrent);
            cardService.saveCard(cardCurrent);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }

        return new ResponseEntity<>("you already have 3 cards created", HttpStatus.FORBIDDEN);
    }

    @PatchMapping("/cards")
    public ResponseEntity<Object> deletecard(Authentication authentication, @RequestParam long id){

        Client clientCurrent = clientService.getClientCurrent(authentication);
        Card cardE = cardService.getById(id);

        if(cardE == null){
            return new ResponseEntity<>("no encontro card", HttpStatus.FORBIDDEN);
        }
        if(!cardE.isActive()){
            return new ResponseEntity<>("Invalid", HttpStatus.FORBIDDEN);
        }
        if (!clientCurrent.getCards().contains(cardE)){
            return new ResponseEntity<>("Invalid", HttpStatus.FORBIDDEN);
        }

        cardE.setActive(false);
        cardService.saveCard(cardE);
        return new ResponseEntity<>("Created",HttpStatus.CREATED);
    }

    @Transactional
    @PostMapping("clients/current/cardTransaction")
    public ResponseEntity<Object> cardTransaction(@RequestBody CardTransactionDTO cardTransactionDTO){


        Card card = cardService.getByNumber(cardTransactionDTO.getNumber());
        Client client = card.getCardHolder();
        List<Account> accounts = client.getAccounts().stream().collect(Collectors.toList());
        Account account = accounts.stream().max(Comparator.comparingDouble(Account::getBalance)).get();


        if(!client.getAccounts().contains(account)){
            return new ResponseEntity<>("Card does not belong to the customer", HttpStatus.FORBIDDEN);
        }
        if(cardTransactionDTO.getAmount() > account.getBalance()){
            return new ResponseEntity<>("Account does not have enough amount", HttpStatus.FORBIDDEN);
        }
        if(cardTransactionDTO.getCvv() != card.getCvv()){
            return new ResponseEntity<>("Error cvv", HttpStatus.FORBIDDEN);
        }
        if(cardTransactionDTO.getType() != card.getType()){
            return new ResponseEntity<>("Error card type", HttpStatus.FORBIDDEN);
        }
        if(card.getThruDate().isBefore(LocalDate.now())){
            return new ResponseEntity<>("Card Expired", HttpStatus.FORBIDDEN);
        }
        if(cardTransactionDTO.getThruDate().getYear() != card.getThruDate().getYear() && cardTransactionDTO.getThruDate().getMonthValue() != cardTransactionDTO.getThruDate().getMonthValue()){
            return new ResponseEntity<>("Error thru date", HttpStatus.FORBIDDEN);
        }
        if(!(client.getFirstName() + client.getLastName()).equals(cardTransactionDTO.getCardHolder())){
            return new ResponseEntity<>("Error client name", HttpStatus.FORBIDDEN);
        }


        Transaction newTransaction = new Transaction(TransactionType.DEBITO,- cardTransactionDTO.getAmount(), cardTransactionDTO.getDescription(), LocalDateTime.now(),account);
        transactionService.saveTransaction(newTransaction);

        account.setBalance(-cardTransactionDTO.getAmount());
        accountService.saveAccount(account);

        return new ResponseEntity<>("Created",HttpStatus.CREATED);

    }


}
