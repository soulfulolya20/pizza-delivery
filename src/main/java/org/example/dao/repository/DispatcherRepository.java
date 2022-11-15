package org.example.dao.repository;

import org.example.models.entity.*;

import java.util.List;
import java.util.Optional;

public interface DispatcherRepository {

    Optional<DispatcherEntity> getDispatcherById(Long dispatcherId);

    void insertDispatcher(DispatcherRequest request);

    List<String> findAllDispatchers();

    void updateDispatcher(DispatcherRequest request, Long dispatcherId);

    void deleteDispatcherById(Long dispatcherId);
}
