package br.com.sapataria.controllers;

import br.com.sapataria.entity.Client;
import br.com.sapataria.services.ClientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.slf4j.helpers.Reporter.error;

@RestController
@RequestMapping("/clients")
@AllArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class ClientController {

    private ClientService clientService;

    @PostMapping("/create")
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        try {
            clientService.save(client);
            return ResponseEntity.ok(client);
        } catch (Exception e) {
            // Log the exception
            error("Error creating client", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Client>> getAll() {
        if (clientService.findAll().isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(clientService.findAll());
        }
    }

    @CrossOrigin
    @GetMapping("/search/{query}")
    public ResponseEntity<Optional<Client>> searchClientByCriteria(@PathVariable String query) {

        Optional<Client> client = Optional.empty();

        if (query.contains("@")) {
            client = clientService.findByEmail(query);
            System.out.println(client.isPresent());

        } else if (query.length() == 11 && query.charAt(2) == '9') {
            client = clientService.findByPhoneNumber(query);

        } else if (query != null) {

            client = clientService.findByName(query);
            System.out.println(client.isPresent());

        }
        return client.isPresent() ? ResponseEntity.ok(client) : ResponseEntity.noContent().build();
    }

    @CrossOrigin
    @GetMapping("id/{id}")
    public ResponseEntity<Optional<Client>> getClientById(@PathVariable String id) {
        if (clientService.findById(id).isPresent()) {
            return ResponseEntity.ok(clientService.findById(id));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @CrossOrigin
    @GetMapping("/email/{email}")
    public ResponseEntity<Optional<Client>> getClientByEmail(@PathVariable String email) {
        if (clientService.findByEmail(email).isPresent()) {
            return ResponseEntity.ok(clientService.findByEmail(email));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/phone-number/{phoneNumber}")
    public ResponseEntity<Optional<Client>> getClientByPhone(@PathVariable String phoneNumber) {
        if (clientService.findByPhoneNumber(phoneNumber).isPresent()) {
            return ResponseEntity.ok(clientService.findByPhoneNumber(phoneNumber));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete-client/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable String id) {
        boolean isDeleted = clientService.deleteById(id);
        if (!(isDeleted)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/delete-client/email/{email}")
    public ResponseEntity<Void> deleteClientByEmail(@PathVariable String email) {
        boolean isDeleted = clientService.deleteByEmail(email);
        if (!(isDeleted)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.noContent().build();
        }

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable String id, @RequestBody Client updatedClient) {
        updatedClient.setId(id);
        Optional<Client> clientOptional = clientService.findById(id);
        if (clientOptional.isPresent()) {
            clientService.updateClient(updatedClient);
            return ResponseEntity.ok(updatedClient);
        } else {
            return ResponseEntity.notFound().build();
        }

    }
}
