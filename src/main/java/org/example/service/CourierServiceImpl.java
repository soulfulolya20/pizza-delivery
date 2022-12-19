package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.repository.CourierRepository;
import org.example.exception.CourierNotFoundException;
import org.example.models.entity.CourierEntity;
import org.example.models.entity.CourierRequest;
import org.example.service.api.CourierService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourierServiceImpl implements CourierService {

    private final CourierRepository courierRepository;

    @Override
    public Optional<CourierEntity> getCourier(Long courierId) {
        return Optional.ofNullable(courierRepository.getCourierById(courierId).orElseThrow(() -> new CourierNotFoundException(courierId)));
    }

    @Override
    public void createCourier(CourierRequest request) {
        courierRepository.insertCourier(request);
    }

    @Override
    public List<String> getAllCouriers() {
        return courierRepository.findAllCouriers();
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

    @Override
    public CourierEntity getCourierByUserId(Long userId) {
        return courierRepository.getCourierByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Не найден курьер с userId=" + userId));
    }

    @Override
    public Boolean isCourier(Long userId) {
        return courierRepository.isCourier(userId);
    }
}

