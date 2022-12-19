package org.example.service.api;

import org.example.models.entity.ClientEntity;
import org.example.models.entity.ProfileRequest;
import org.example.models.entity.ProfileUpdateRequest;

import java.util.List;

public interface ClientService {
    ClientEntity getProfile(Long clientId);

    List<String> getAllClients();
    void createClient(ProfileRequest request);
    void updateClient(ProfileUpdateRequest request);
    
    void deleteProfile(Long clientId);

    ClientEntity getClientByUserId(Long userId);

    ClientEntity getCurrentClient();
}
