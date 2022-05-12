package io.github.willianlds.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "description", length = 100)
    @NotEmpty(message = "{field.description.required}")
    private String description;

    @Column(name = "price_unitary")
    @NotNull(message = "{field.price.required}")
    private BigDecimal price;

}
