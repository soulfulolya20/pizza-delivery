package org.example.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PizzaOrderRequestDTO {

    private Long pizzaId;

    private Long amount;
}
