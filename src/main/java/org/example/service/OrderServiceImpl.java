package org.example.service;

import org.example.dao.repository.OrderRepository;
import org.example.exception.DispatcherNotFoundException;
import org.example.exception.OrderNotFoundException;
import org.example.models.entity.OrderEntity;
import org.example.models.entity.OrderRequest;
import org.example.service.api.OrderService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Primary
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Optional<OrderEntity> getOrder(Long orderId) {
        return Optional.ofNullable(orderRepository.getOrderById(orderId).orElseThrow(() -> new DispatcherNotFoundException(orderId)));
    }

    @Override
    public void createOrder(OrderRequest request) {
        orderRepository.insertOrder(request);
    }

    @Override
    public void updateOrderCourier(Long courierId, Long orderId) {
        OrderEntity order = orderRepository.getOrderById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
        orderRepository.updateOrderCourier(courierId, orderId);
    }

    @Override
    public void updateOrderIsFormed(Boolean isFormed, Long orderId) {
        OrderEntity order = orderRepository.getOrderById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
        orderRepository.updateOrderIsFormed(isFormed, orderId);
    }

    @Override
    public void updateOrderIsComplete(Boolean isComplete, Long orderId) {
        OrderEntity order = orderRepository.getOrderById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
        orderRepository.updateOrderIsComplete(isComplete, orderId);
    }

    @Override
    public List<String> getAllOrders() {
        return orderRepository.findAllOrders();
    }

    @Override
    public void deleteOrder(Long orderId) {
        OrderEntity order = orderRepository.getOrderById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
        orderRepository.deleteOrderById(orderId);
    }
}
