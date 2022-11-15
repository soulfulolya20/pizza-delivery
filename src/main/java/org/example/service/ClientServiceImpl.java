package org.example.service;

import org.example.exception.ClientNotFoundException;
import org.example.models.entity.ClientEntity;
import org.example.dao.repository.ClientRepository;
import org.example.models.entity.ProfileRequest;
import org.example.models.entity.ProfileUpdateRequest;
import org.example.service.api.ClientService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Primary
@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public ClientEntity getProfile(Long clientId) {
        return
                clientRepository.getClientById(clientId).orElseThrow(() -> new ClientNotFoundException(clientId));
    }

    @Override
    public List<String> getAllClients() {
        return clientRepository.findAllClients();
    }

    @Override
    public void createClient(ProfileRequest request) {
        clientRepository.insertClient(request);
    }

    @Override
    public void updateClient(ProfileUpdateRequest request) {
        ClientEntity client = clientRepository.getClientById(request.clientId())
                .orElseThrow(() -> new ClientNotFoundException(request.clientId()));
        clientRepository.updateClient(request);
    }

    @Override
    public void deleteProfile(Long clientId) {
        var profile = clientRepository.getClientById(clientId).orElseThrow(() -> new ClientNotFoundException(clientId));
        clientRepository.deleteClientById(profile.clientId());
    }
}
