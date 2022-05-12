package io.github.willianlds.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InfoOrderDTO {
    private Integer cod;
    private String cpf;
    private String nameClient;
    private BigDecimal totalOrder;
    private String dateOrder;
    private String status;
    private List<InfoItemOrderDTO> items;
}
