package org.example.service.api;

import org.example.models.entity.*;

import java.util.List;
import java.util.Optional;

public interface DispatcherService {
    Optional<DispatcherEntity> getDispatcher(Long dispatcherId);
    void createDispatcher(DispatcherRequest request);
    void updateDispatcher(DispatcherRequest request, Long dispatcherId);

    List<String> getAllDispatchers();
    void deleteDispatcher(Long dispatcherId);

    DispatcherEntity getDispatcherByUserId(Long userId);
}
