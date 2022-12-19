package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.aspect.AuthRole;
import org.example.models.dto.OrderResponseDTO;
import org.example.models.dto.PlaceOrderRequestDTO;
import org.example.models.entity.CourierEntity;
import org.example.models.entity.OrderEntity;
import org.example.models.entity.OrderRequest;
import org.example.models.entity.UserEntity;
import org.example.models.enums.ScopeType;
import org.example.service.api.CourierService;
import org.example.service.api.OrderService;
import org.example.service.api.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    private final UserService userService;

    private final CourierService courierService;

    @GetMapping
    public List<String> getAllOrders() { return orderService.getAllOrders(); }

    @GetMapping(value = "/{orderId}")
    public Optional<OrderEntity> getOrder(@PathVariable("orderId") Long orderId) {
        return orderService.getOrder(orderId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@Valid @RequestBody OrderRequest request) {
        orderService.createOrder(request);
    }

    @PutMapping(value = "/change-courier/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCourier(@Valid @RequestParam Long courierId, @PathVariable("orderId") Long orderId) {
        orderService.updateOrderCourier(courierId, orderId);
    }

    @PutMapping(value = "/complete/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateIsComplete(@Valid @RequestParam Boolean isComplete, @PathVariable("orderId") Long orderId) {
        orderService.updateOrderIsComplete(isComplete, orderId);
    }

    @DeleteMapping(value = "/{orderId}")
    public void deleteOrder(@PathVariable Long orderId) { orderService.deleteOrder(orderId); }

    @PostMapping(value = "/place-order")
    public void placeOrder(@RequestBody PlaceOrderRequestDTO request) {
        orderService.placeOrder(request);
    }

    @GetMapping(value = "/history")
    public List<OrderResponseDTO> getOrderHistory() {
        return orderService.getOrderHistory();
    }

    @GetMapping(value = "/available/{status}")
    @AuthRole(roles = ScopeType.COURIER)
    public List<OrderResponseDTO> getAvailableOrders(@PathVariable("status") String status) {
        return orderService.getAvailableOrders(status);
    }

    @PostMapping(value = "/{orderId}/claim")
    @AuthRole(roles = ScopeType.COURIER)
    public void claimOrder(@PathVariable("orderId") Long orderId) {
        orderService.claimOrder(orderId);
    }

    @PostMapping(value = "/{orderId}/cook")
    @AuthRole(roles = ScopeType.DISPATCHER)
    public void cookOrder(@PathVariable("orderId") Long orderId) {
        orderService.cookOrder(orderId);
    }

    @GetMapping(value = "/current")
    @AuthRole(roles = ScopeType.COURIER)
    public OrderResponseDTO getCurrentOrder() {
        UserEntity user = userService.getCurrentUser();
        CourierEntity courier = courierService.getCourierByUserId(user.getUserId());
        return orderService.getCurrentOrderByCourierId(courier.courierId());
    }

    @PostMapping(value = "/{orderId}/deliver")
    @AuthRole(roles = {ScopeType.COURIER, ScopeType.DISPATCHER})
    public void deliverOrder(@PathVariable("orderId") Long orderId) {
        orderService.deliverOrder(orderId);
    }
}
