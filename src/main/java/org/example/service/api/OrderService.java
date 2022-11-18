package org.example.service.api;

import org.example.models.entity.OrderEntity;
import org.example.models.entity.OrderRequest;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    Optional<OrderEntity> getOrder(Long orderId);

    void createOrder(OrderRequest request);

    void updateOrderCourier(Long courierId, Long orderId);

    void updateOrderIsFormed(Boolean isFormed, Long orderId);

    void updateOrderIsComplete(Boolean isComplete, Long orderId);

    List<String> getAllOrders();

    void deleteOrder(Long orderId);
}
