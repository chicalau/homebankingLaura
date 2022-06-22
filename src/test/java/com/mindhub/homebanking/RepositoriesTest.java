package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@SpringBootTest
@AutoConfigureTestDatabase(replace = NONE)
public class RepositoriesTest {

    @Autowired
    LoanRepository loanRepository;
    @Autowired
    ClientRepository clientRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Test
    public void existLoans(){
        List<Loan> loans = loanRepository.findAll();

        assertThat(loans,is(not(empty())));
    }

    @Test
    public void existPersonalLoan(){
        List<Loan> loans = loanRepository.findAll();

        assertThat(loans, hasItem(hasProperty("name", is("Personal"))));
    }

    @Test
    public void existPasswordClient(){
        List<Client> clients = clientRepository.findAll();

        assertThat(clients, everyItem(hasProperty("password")));
    }

    @Test
    public void existClients(){
        List<Client> clients = clientRepository.findAll();

        assertThat(clients,is(not(empty())));
    }

    @Test
    public void accountClient(){
        List<Account> accounts = accountRepository.findAll();

        assertThat(accounts, everyItem(hasProperty("client", instanceOf(Client.class))));
    }

    @Test
    public void cardClient(){
        List<Card> cards = cardRepository.findAll();

        assertThat(cards, everyItem(hasProperty("cardHolder", instanceOf(Client.class))));
    }

    @Test
    public void transactionAccount(){
        List<Transaction> transactions = transactionRepository.findAll();

        assertThat(transactions, everyItem(hasProperty("account_id", instanceOf(Account.class))));
    }

    @Test
    public void existAccounts (){
        List<Account> accounts = accountRepository.findAll();

        assertThat(accounts, is(not(empty())));
    }

    @Test
    public void existAdmin () {
        List<Client> clients = clientRepository.findAll();

        assertThat(clients, hasItem(hasProperty("email",containsString("@admin"))));
    }

    @Test
    public void accountBalance (){
        List<Account> accounts = accountRepository.findAll();
        assertThat(accounts, everyItem(hasProperty("balance",is(greaterThanOrEqualTo(0.0)))));
    }

    @Test
    public void transactionType (){
        List<Transaction> transactions = transactionRepository.findAll();

        assertThat(transactions, everyItem(hasProperty("type", isA(TransactionType.class) )));
    }

    @Test
    public void cardType (){
        List<Card> cards = cardRepository.findAll();

        assertThat(cards, everyItem(hasProperty("type", isA(CardType.class) )));
    }

    @Test
    public void cardColor (){
        List<Card> cards = cardRepository.findAll();

        assertThat(cards, everyItem(hasProperty("color", isA(CardColor.class) )));
    }



}
