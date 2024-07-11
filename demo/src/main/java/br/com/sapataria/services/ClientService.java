package br.com.sapataria.services;

import br.com.sapataria.entity.Client;
import br.com.sapataria.repositories.ClientRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ClientService {

    private static final Logger log = LoggerFactory.getLogger(ClientService.class);
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

    public Optional<Client> findById(String id) {
        if (clientRepository.findById(id).isEmpty()) {
            return Optional.empty();
        } else {
            return clientRepository.findById(id);
        }
    }

    public Optional<Client> findByEmail(String email) {
        if (clientRepository.findByEmail(email) != null) {
            return Optional.of(clientRepository.findByEmail(email));
        } else {
            return Optional.empty();
        }
    }

    public Optional<Client> findByPhoneNumber(String phone) {
        if (clientRepository.findByPhoneNumber(phone) != null) {
            return Optional.of(clientRepository.findByPhoneNumber(phone));
        } else {
            return Optional.empty();
        }
    }

    public boolean deleteById(String id) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteByEmail(String email) {
        if (clientRepository.existsByEmail(email)) {
            clientRepository.deleteByEmail(email);
            return true;
        } else {
            return false;
        }
    }

    public void updateClient(Client client) {
        if (clientRepository.existsById(client.getId())) {
            clientRepository.save(client);
        } else {
            log.error("Client not found!");
        }
    }
}
