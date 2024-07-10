package br.com.sapataria.controllers;

import br.com.sapataria.entity.Client;
import br.com.sapataria.services.ClientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
