package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.exception.ClientNotFoundException;
import org.example.models.entity.ClientEntity;
import org.example.models.entity.UserEntity;
import org.example.repository.ClientRepository;
import org.example.models.entity.ProfileRequest;
import org.example.models.entity.ProfileUpdateRequest;
import org.example.service.api.AuthService;
import org.example.service.api.ClientService;
import org.example.service.api.UserService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    private final UserService userService;

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

    @Override
    public ClientEntity getClientByUserId(Long userId) {
        return clientRepository.getClientByUserId(userId).orElseThrow(() -> new RuntimeException("Не найден клиент с userId=" + userId));
    }

    @Override
    public ClientEntity getCurrentClient() {
        UserEntity user = userService.getCurrentUser();
        return getClientByUserId(user.getUserId());
    }
}
