package io.github.willianlds.rest.dto;

import io.github.willianlds.validation.NotEmptyList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    @NotNull(message = "{field.cod-client.required}")
    private Integer client;
    @NotNull(message = "{field.total-order.required}")
    private BigDecimal total;
    @NotEmptyList(message = "{field.items-order.required}")
    private List<ItemsOrderDTO> items;

}
