package io.github.willianlds.entity;

import io.github.willianlds.enums.StatusOrder;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_sale")
public class OrderSale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "client_id ")
    private Client client;

    @Column(name = "date_order")
    private LocalDate dateOrder;

    @Column(name = "total", length = 2, precision = 20, scale = 2)
    @NotNull(message = "{field.total-order.required}")
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusOrder status;

    @OneToMany(mappedBy = "orderSale")
    private List<ItemOrder> items;

}
