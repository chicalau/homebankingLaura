
package com.mindhub.homebanking.DTO;


import com.mindhub.homebanking.models.Client;


import java.util.Set;
import java.util.stream.Collectors;


import static java.util.stream.Collectors.toSet;

public class ClientDTO {

    private long id;
    private String firstName, lastName, email;
    private Set<AccountDTO> accounts;

    private Set<ClientLoanDTO> loans;

    private Set<CardDTO> cards;

    public ClientDTO(Client client) {
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.accounts = client.getAccounts().stream().filter(account -> account.isActive()).map(account -> new AccountDTO(account)).collect(toSet());
        this.loans = client.getClientLoans().stream().map(clientLoan -> new ClientLoanDTO(clientLoan)).collect(toSet());
        this.cards = client.getCards().stream().filter(card -> card.isActive()).map(card -> new CardDTO(card)).collect(toSet());
    }
    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public Set<AccountDTO> getAccounts() {
        return accounts;
    }

    public Set<ClientLoanDTO> getLoans() {
        return loans;
    }
    public void setLoans(Set<ClientLoanDTO> loans) {
        this.loans = loans;
    }


    public Set<CardDTO> getCards() {
        return cards;
    }
    public void setCards(Set<CardDTO> cards) {
        this.cards = cards;
    }
}
