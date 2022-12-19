package org.example.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaceOrderRequestDTO {

    private String note;

    private String address;

    private List<PizzaOrderRequestDTO> items;

    private Long clientId;
}
