package org.example.controller;

import org.example.models.entity.CourierEntity;
import org.example.models.entity.CourierRequest;
import org.example.service.api.CourierService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/courier")
public class CourierController {
    private final CourierService courierService;


    public CourierController(CourierService courierService) {
        this.courierService = courierService;
    }

    @GetMapping(value = "/{courierId}")
    public Optional<CourierEntity> getCourier(@PathVariable("courierId") Long courierId) {
        return courierService.getCourier(courierId);
    }

    @GetMapping
    public List<String> getAllCouriers() {
        return courierService.getAllCouriers();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createCourier(@Valid @RequestBody CourierRequest request) {
        courierService.createCourier(request);
    }

    @PutMapping(value = "/{courierId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCourier(@Valid @RequestBody CourierRequest request, @PathVariable("courierId") Long courierId) {
        courierService.updateCourier(request, courierId);
    }

    @DeleteMapping(value = "/{courierId}")
    public void deleteCourier(@PathVariable Long courierId) {
        courierService.deleteCourier(courierId);
    }
}
