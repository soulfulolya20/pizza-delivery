package org.example.controller;

import org.example.models.entity.CourierRequest;
import org.example.models.entity.OrderEntity;
import org.example.models.entity.OrderRequest;
import org.example.service.api.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) { this.orderService = orderService; }

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

    @PutMapping(value = "/form/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateIsFormed(@Valid @RequestParam Boolean isFormed, @PathVariable("orderId") Long orderId) {
        orderService.updateOrderIsFormed(isFormed, orderId);
    }

    @PutMapping(value = "/complete/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateIsComplete(@Valid @RequestParam Boolean isComplete, @PathVariable("orderId") Long orderId) {
        orderService.updateOrderIsComplete(isComplete, orderId);
    }

    @DeleteMapping(value = "/{orderId}")
    public void deleteOrder(@PathVariable Long orderId) { orderService.deleteOrder(orderId); }
}
