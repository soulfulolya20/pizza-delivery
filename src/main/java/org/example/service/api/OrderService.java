package org.example.service.api;

import org.example.models.dto.OrderResponseDTO;
import org.example.models.dto.PizzaOrderItemResponseDTO;
import org.example.models.dto.PlaceOrderRequestDTO;
import org.example.models.entity.OrderEntity;
import org.example.models.entity.OrderRequest;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    Optional<OrderEntity> getOrder(Long orderId);

    void createOrder(OrderRequest request);

    void updateOrderCourier(Long courierId, Long orderId);

    void updateOrderIsComplete(Boolean isComplete, Long orderId);

    List<String> getAllOrders();

    void deleteOrder(Long orderId);

    Long placeOrder(PlaceOrderRequestDTO request);

    List<OrderResponseDTO> getOrderHistory();

    List<PizzaOrderItemResponseDTO> getOrderItems(Long orderId);

    List<OrderResponseDTO> getAvailableOrders();

    void claimOrder(Long orderId);

    OrderResponseDTO getCurrentOrderByCourierId(Long courierId);

    void deliverOrder(Long orderId);
}
