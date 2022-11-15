package org.example.controller;

import org.example.models.entity.ClientEntity;
import org.example.models.entity.ProfileRequest;
import org.example.models.entity.ProfileUpdateRequest;
import org.example.service.api.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/client")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public List<String> getAllClients() {
        return clientService.getAllClients();
    }

    @GetMapping(value = "/{clientId}")
    public ClientEntity getProfile(@PathVariable("clientId") Long clientId) {
        return clientService.getProfile(clientId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProfile(@Valid @RequestBody ProfileRequest request) {
        clientService.createClient(request);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateClient(@Valid @RequestBody ProfileUpdateRequest request) {
        clientService.updateClient(request);
    }

    @DeleteMapping(value = "/{clientId}")
    public void deleteProfile(@PathVariable Long clientId) {
        clientService.deleteProfile(clientId);
    }
}
