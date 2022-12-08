package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.models.entity.PizzaEntity;
import org.example.service.api.PizzaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pizza")
@RequiredArgsConstructor
public class PizzaController {

    private final PizzaService pizzaService;

    @GetMapping
    public List<PizzaEntity> getAllPizza() {
        return pizzaService.getAllPizza();
    }

    @GetMapping("/{id}")
    public PizzaEntity getById(@PathVariable Long id) {
        return pizzaService.getById(id);
    }
}
