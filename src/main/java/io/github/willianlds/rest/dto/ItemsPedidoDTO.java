package io.github.willianlds.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemsPedidoDTO {

    private Integer produto;
    private Integer quantidade;

}
