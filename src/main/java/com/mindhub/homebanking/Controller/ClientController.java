package com.mindhub.homebanking.Controller;
import com.mindhub.homebanking.DTO.ClientDTO;
import com.mindhub.homebanking.Utils.CardUtils;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/clients")
    public List<ClientDTO> getAll() {
        return clientService.getClientsDTO();
    }

    @GetMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id){
        return clientService.getClientDTO(id);
    }

    int min =00000000;
    int max =99999999;

    String accountNumber = String.valueOf(CardUtils.getRandomNumber(min, max));

    @PostMapping("/clients")
    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password) {

        if ( firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (email.contains(" ")){
            return new ResponseEntity<>("Invalid email", HttpStatus.FORBIDDEN);
        }

        if(!email.contains("@") && !email.contains(".")){
            return new ResponseEntity<>("Invalid email", HttpStatus.FORBIDDEN);
        }

        if (clientService.getClientByEmail(email) != null ) {
            return new ResponseEntity<>("email already in use", HttpStatus.FORBIDDEN);
        }

        Client clientR = new Client(firstName, lastName, email, passwordEncoder.encode(password));
        clientService.saveClient(clientR);
        Account accountR = new Account ("VIN"+ accountNumber, LocalDate.now(),0, clientR, AccountType.AHORRO);
        accountService.saveAccount(accountR);
        return new ResponseEntity<>("Created",HttpStatus.CREATED);
    }

    @GetMapping("/clients/current")
    public ClientDTO getClient (Authentication authentication) {
        Client clientnew = clientService.getClientCurrent(authentication);
        return new ClientDTO(clientnew);
    }


}