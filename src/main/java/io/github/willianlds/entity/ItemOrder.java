package io.github.willianlds.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "item_order")
public class ItemOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderSale orderSale;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column
    private Integer quantity;

}
