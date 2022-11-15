package org.example.dao.repository;

import org.example.models.entity.*;

import java.util.Optional;

public interface DispatcherRepository {

    Optional<DispatcherEntity> getDispatcherById(Long dispatcherId);

    void insertDispatcher(DispatcherRequest request);

    void updateDispatcher(DispatcherRequest request, Long dispatcherId);

    void deleteDispatcherById(Long dispatcherId);
}
