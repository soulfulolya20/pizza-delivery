package org.example.models.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PizzaEntity(
        Long pizzaId,
        Double pizzaWeight,
        Double pizzaPrice,
        String pizzaName,
        int pizzaSize,
        Boolean isAvailable,
        String imageUrl
) {
}
