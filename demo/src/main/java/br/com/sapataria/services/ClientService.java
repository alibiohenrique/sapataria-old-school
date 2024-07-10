package br.com.sapataria.services;

import br.com.sapataria.entity.Client;
import br.com.sapataria.repositories.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public void save(Client client) {

        clientRepository.save(client);
    }
}
