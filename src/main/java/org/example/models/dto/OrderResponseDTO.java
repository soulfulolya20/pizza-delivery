package org.example.models.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.example.models.enums.StatusType;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResponseDTO {

    private Long orderId;

    private LocalDateTime formedDttm;

    private List<PizzaOrderItemResponseDTO> items;

    private Double total;

    private StatusType status;

    private String address;

    private String note;
}
