package br.com.sapataria.services;

import br.com.sapataria.entity.Client;
import br.com.sapataria.repositories.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public void save(Client client) {

        clientRepository.save(client);

    }

    public List<Client> findAll() {
        if (clientRepository.findAll().isEmpty()) {
            return null;
        } else {
            return clientRepository.findAll();

        }
    }
    
}
