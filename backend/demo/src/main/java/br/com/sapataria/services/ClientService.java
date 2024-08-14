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
        return clientRepository.findById(id);
    }

    public Optional<Client> findByName(String name) {
        return Optional.ofNullable(clientRepository.findByName(name));
    }

    public Optional<Client> findByEmail(String email) {
        return Optional.ofNullable(clientRepository.findByEmail(email));
    }

    public Optional<Client> findByPhoneNumber(String phone) {
        return Optional.ofNullable(clientRepository.findByPhoneNumber(phone));
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
