package org.example.service.api;

import org.example.models.entity.CourierEntity;
import org.example.models.entity.CourierRequest;

import java.util.List;
import java.util.Optional;

public interface CourierService {
    Optional<CourierEntity> getCourier(Long courierId);
    void createCourier(CourierRequest request);

    List<String> getAllCouriers();
    void updateCourier(CourierRequest request, Long courierId);
    void deleteCourier(Long courierId);

    CourierEntity getCourierByUserId(Long userId);

    Boolean isCourier(Long userId);

    void deleteCourierByUserId(Long userId);
}
