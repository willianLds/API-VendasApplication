package io.github.willianlds.service;

import io.github.willianlds.entity.OrderSale;
import io.github.willianlds.enums.StatusOrder;
import io.github.willianlds.rest.dto.OrderDTO;

import java.util.Optional;

public interface OrderService {
    OrderSale save(OrderDTO dto);
    Optional<OrderSale> getOrderComplete(Integer id);
    void updateStatus(Integer id, StatusOrder statusOrder);
}
