package org.example.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class PizzaOrderItemResponseDTO {

    private String pizzaName;

    private Double pizzaPrice;

    private Long amount;

}
