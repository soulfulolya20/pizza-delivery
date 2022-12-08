package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.models.entity.PizzaEntity;
import org.example.repository.PizzaRepository;
import org.example.service.api.PizzaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PizzaServiceImpl implements PizzaService {

    private final PizzaRepository pizzaRepository;

    @Override
    public List<PizzaEntity> getAllPizza() {
        return pizzaRepository.getAllPizza();
    }


    @Override
    public PizzaEntity getById(Long id) {
        return pizzaRepository.getById(id);
    }
}
