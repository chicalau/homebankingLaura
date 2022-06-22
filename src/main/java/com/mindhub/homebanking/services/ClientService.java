package com.mindhub.homebanking.services;

import com.mindhub.homebanking.DTO.ClientDTO;
import com.mindhub.homebanking.models.Client;
import org.springframework.security.core.Authentication;


import java.util.List;

public interface ClientService {

    List<ClientDTO> getClientsDTO();

    ClientDTO getClientDTO(long id);

    Client getClientCurrent(Authentication authentication);

    Client getClientByEmail(String email);

    void saveClient(Client client);




}
