package org.example.service.api;

import org.example.models.entity.*;

import java.util.Optional;

public interface DispatcherService {
    Optional<DispatcherEntity> getDispatcher(Long dispatcherId);
    void createDispatcher(DispatcherRequest request);
    void updateDispatcher(DispatcherRequest request, Long dispatcherId);
    void deleteDispatcher(Long dispatcherId);
}
