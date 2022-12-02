package org.example.repository;

import org.example.models.entity.ClientEntity;
import org.example.models.entity.ProfileRequest;
import org.example.models.entity.ProfileUpdateRequest;

import java.util.List;
import java.util.Optional;

public interface ClientRepository {

    Optional<ClientEntity> getClientById(Long id);
    void insertClient(ProfileRequest request);

    List<String> findAllClients();
    void updateClient(ProfileUpdateRequest request);

    void deleteClientById(Long id);
}
