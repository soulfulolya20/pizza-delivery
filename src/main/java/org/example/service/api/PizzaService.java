package org.example.service.api;

import org.example.models.entity.PizzaEntity;

import java.util.List;

public interface PizzaService {

    List<PizzaEntity> getAllPizza();

    PizzaEntity getById(Long id);
}
