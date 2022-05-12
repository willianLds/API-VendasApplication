package io.github.willianlds.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InfoItemOrderDTO {
    private String descriptionProduct;
    private BigDecimal priceUnitary;
    private Integer quantity;
}
