package org.example.repository;

import org.example.models.entity.CourierEntity;
import org.example.models.entity.CourierRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface CourierRepository {

    Optional<CourierEntity> getCourierById(Long courierId);

    void insertCourier(CourierRequest request);

    List<String> findAllCouriers();

    void updateCourier(CourierRequest request, Long courierId);

    void deleteCourierById(Long courierId);
}

