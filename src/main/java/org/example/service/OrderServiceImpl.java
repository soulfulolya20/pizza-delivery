package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.exception.DispatcherNotFoundException;
import org.example.exception.OrderNotFoundException;
import org.example.models.dto.OrderResponseDTO;
import org.example.models.dto.PizzaOrderItemResponseDTO;
import org.example.models.dto.PlaceOrderRequestDTO;
import org.example.models.entity.ClientEntity;
import org.example.models.entity.CourierEntity;
import org.example.models.entity.OrderEntity;
import org.example.models.entity.OrderRequest;
import org.example.models.entity.UserEntity;
import org.example.models.enums.StatusType;
import org.example.repository.OrderRepository;
import org.example.service.api.ClientService;
import org.example.service.api.CourierService;
import org.example.service.api.OrderService;
import org.example.service.api.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final ClientService clientService;

    private final UserService userService;

    private final CourierService courierService;

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

    @Transactional
    @Override
    public Long placeOrder(PlaceOrderRequestDTO request) {
        ClientEntity client = clientService.getCurrentClient();
        request.setClientId(client.clientId());

        Long orderId = orderRepository.insertOrder(new OrderRequest(client.clientId(), null, null,
                request.getNote(), StatusType.FORMED, request.getAddress()));
        orderRepository.insertOrderItems(orderId, request.getItems());

        return orderId;
    }

    @Override
    public List<OrderResponseDTO> getOrderHistory() {
        ClientEntity client = clientService.getCurrentClient();
        List<OrderEntity> orders = getClientOrders(client.clientId());
        return orders.stream().map(orderEntity -> {
            Double total = 0.0;
            OrderResponseDTO orderDTO = new OrderResponseDTO();
            orderDTO.setOrderId(orderEntity.orderId());
            orderDTO.setStatus(orderEntity.status());
            orderDTO.setFormedDttm(orderEntity.formedDttm());
            orderDTO.setItems(getOrderItems(orderEntity.orderId()));
            orderDTO.setTotal(orderDTO.getItems().stream().map(pizza -> pizza.getPizzaPrice() * pizza.getAmount()).reduce(total, Double::sum) + 29);

            return orderDTO;
        }).collect(Collectors.toList());

    }

    private List<OrderEntity> getClientOrders(Long clientId) {
        return orderRepository.getClientOrders(clientId);
    }

    @Override
    public List<PizzaOrderItemResponseDTO> getOrderItems(Long orderId) {
        return orderRepository.getOrderItems(orderId);
    }

    @Override
    public List<OrderResponseDTO> getAvailableOrders(String status) {
        List<OrderEntity> orders = orderRepository.getAvailableOrders(status);
        return orders.stream().map(orderEntity -> {
            OrderResponseDTO orderDTO = new OrderResponseDTO();
            orderDTO.setOrderId(orderEntity.orderId());
            orderDTO.setFormedDttm(orderEntity.formedDttm());
            orderDTO.setItems(getOrderItems(orderEntity.orderId()));
            orderDTO.setAddress(orderEntity.address());
            orderDTO.setNote(orderEntity.note());
            return orderDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public void claimOrder(Long orderId) {
        UserEntity user = userService.getCurrentUser();
        CourierEntity courier = courierService.getCourierByUserId(user.getUserId());
        orderRepository.updateOrderCourier(courier.courierId(), orderId);
    }

    @Override
    public OrderResponseDTO getCurrentOrderByCourierId(Long courierId) {
        OrderEntity orderEntity = orderRepository.getCurrentCourierOrder(courierId);

        if (orderEntity == null) {
            return null;
        }

        OrderResponseDTO orderDTO = new OrderResponseDTO();
        Double total = 0.0;

        orderDTO.setOrderId(orderEntity.orderId());
        orderDTO.setFormedDttm(orderEntity.formedDttm());
        orderDTO.setItems(getOrderItems(orderEntity.orderId()));
        orderDTO.setAddress(orderEntity.address());
        orderDTO.setNote(orderEntity.note());
        orderDTO.setTotal(orderDTO.getItems().stream().map(pizza -> pizza.getPizzaPrice() * pizza.getAmount()).reduce(total, Double::sum) + 29);
        return orderDTO;
    }

    @Override
    public void deliverOrder(Long orderId) {
        orderRepository.changeOrderStatus(orderId, StatusType.COMPLETED);
    }

    @Override
    public void cookOrder(Long orderId) {
        orderRepository.changeOrderStatus(orderId, StatusType.COOKING);
    }

    @Override
    public void cancelClientOrder(Long orderId) {
        ClientEntity client = clientService.getCurrentClient();
        orderRepository.cancelClientOrder(orderId, client.clientId());
    }
}
