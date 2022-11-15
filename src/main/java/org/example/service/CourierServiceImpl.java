package org.example.service;

import org.example.dao.repository.CourierRepository;
import org.example.exception.CourierNotFoundException;
import org.example.models.entity.CourierEntity;
import org.example.models.entity.CourierRequest;
import org.example.service.api.CourierService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Primary
@Service
public class CourierServiceImpl implements CourierService {
    private final CourierRepository courierRepository;

    public CourierServiceImpl(CourierRepository courierRepository) {
        this.courierRepository = courierRepository;
    }


    @Override
    public Optional<CourierEntity> getCourier(Long courierId) {
        return Optional.ofNullable(courierRepository.getCourierById(courierId).orElseThrow(() -> new CourierNotFoundException(courierId)));
    }

    @Override
    public void createCourier(CourierRequest request) {
        courierRepository.insertCourier(request);
    }

    @Override
    public void updateCourier(CourierRequest request, Long courierId) {
        CourierEntity dispatcher = courierRepository.getCourierById(courierId).orElseThrow(() -> new CourierNotFoundException(courierId));
        courierRepository.updateCourier(request, courierId);
    }

    @Override
    public void deleteCourier(Long courierId) {
        var dispatcher = courierRepository.getCourierById(courierId).orElseThrow(() -> new CourierNotFoundException(courierId));
        courierRepository.deleteCourierById(dispatcher.courierId());
    }
}

