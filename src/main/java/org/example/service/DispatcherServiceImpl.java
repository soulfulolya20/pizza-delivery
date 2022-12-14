package org.example.service;

import org.example.repository.DispatcherRepository;
import org.example.exception.DispatcherNotFoundException;
import org.example.models.entity.DispatcherEntity;
import org.example.models.entity.DispatcherRequest;
import org.example.service.api.DispatcherService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Primary
@Service
public class DispatcherServiceImpl implements DispatcherService {

    private final DispatcherRepository dispatcherRepository;

    public DispatcherServiceImpl(DispatcherRepository dispatcherRepository) {
        this.dispatcherRepository = dispatcherRepository;
    }

    @Override
    public Optional<DispatcherEntity> getDispatcher(Long dispatcherId) {
        return Optional.ofNullable(dispatcherRepository.getDispatcherById(dispatcherId).orElseThrow(() -> new DispatcherNotFoundException(dispatcherId)));
    }

    @Override
    public void createDispatcher(DispatcherRequest request) {
        dispatcherRepository.insertDispatcher(request);
    }

    @Override
    public void updateDispatcher(DispatcherRequest request, Long dispatcherId) {
        DispatcherEntity dispatcher = dispatcherRepository.getDispatcherById(dispatcherId).orElseThrow(() -> new DispatcherNotFoundException(dispatcherId));
        dispatcherRepository.updateDispatcher(request, dispatcherId);
    }

    @Override
    public void deleteDispatcher(Long dispatcherId) {
    var dispatcher = dispatcherRepository.getDispatcherById(dispatcherId).orElseThrow(() -> new DispatcherNotFoundException(dispatcherId));
    dispatcherRepository.deleteDispatcherById(dispatcher.dispatcherId());
    }

    @Override
    public DispatcherEntity getDispatcherByUserId(Long userId) {
        return dispatcherRepository.getDispatcherByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Не найден диспетчер с userId=" + userId));
    }

    @Override
    public Boolean isDispatcher(Long userId) {
        return dispatcherRepository.isDispatcher(userId);
    }

    @Override
    public void deleteDispatcherByUserId(Long userId) {
        dispatcherRepository.deleteDispatcherByUserId(userId);
    }

    @Override
    public List<String> getAllDispatchers() {
        return dispatcherRepository.findAllDispatchers();
    }
}
