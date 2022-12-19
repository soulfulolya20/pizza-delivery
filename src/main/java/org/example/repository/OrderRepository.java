package org.example.repository;

import org.example.models.dto.OrderResponseDTO;
import org.example.models.dto.PizzaOrderItemResponseDTO;
import org.example.models.dto.PizzaOrderRequestDTO;
import org.example.models.entity.OrderEntity;
import org.example.models.entity.OrderRequest;
import org.example.models.enums.StatusType;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Optional<OrderEntity> getOrderById(Long orderId);

    Long insertOrder(OrderRequest request);

    List<String> findAllOrders();

    void updateOrderCourier(Long courierId, Long orderId);

    void updateOrderIsComplete(Boolean isComplete, Long orderId);

    void deleteOrderById(Long orderId);

    void insertOrderItems(Long orderId, List<PizzaOrderRequestDTO> items);

    List<PizzaOrderItemResponseDTO> getOrderItems(Long orderId);

    List<OrderEntity> getClientOrders(Long clientId);

    OrderEntity getCurrentCourierOrder(Long courierId);

    List<OrderEntity> getAvailableOrders(String status);

    void changeOrderStatus(Long orderId, StatusType status);
}

