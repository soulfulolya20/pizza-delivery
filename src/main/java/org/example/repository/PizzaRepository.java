package org.example.repository;

import org.example.models.entity.PizzaEntity;

import java.util.List;

public interface PizzaRepository {

    List<PizzaEntity> getAllPizza();

    PizzaEntity getById(Long id);
}
