package org.example.repository;

import org.example.models.entity.OrderEntity;
import org.example.models.entity.OrderRequest;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Optional<OrderEntity> getOrderById(Long orderId);

    void insertOrder(OrderRequest request);

    List<String> findAllOrders();

    void updateOrderCourier(Long courierId, Long orderId);

    void updateOrderIsFormed(Boolean isFormed, Long orderId);

    void updateOrderIsComplete(Boolean isComplete, Long orderId);

    void deleteOrderById(Long orderId);
}

