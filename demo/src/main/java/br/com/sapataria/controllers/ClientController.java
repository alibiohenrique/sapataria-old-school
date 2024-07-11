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
public class ClientController {

    private ClientService clientService;

    @PostMapping("/create")
    public ResponseEntity<Void> createClient(@RequestBody Client client) {
        try {
            clientService.save(client);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            // Log the exception
            error("Error creating client", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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

    @GetMapping("id/{id}")
    public ResponseEntity<Optional<Client>> getClientById(@PathVariable String id) {
        if (clientService.findById(id).isPresent()) {
            return ResponseEntity.ok(clientService.findById(id));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

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
        if(clientService.findByPhoneNumber(phoneNumber).isPresent()) {
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
}
