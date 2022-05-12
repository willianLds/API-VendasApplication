package io.github.willianlds.service.impl;

import io.github.willianlds.entity.Client;
import io.github.willianlds.entity.ItemOrder;
import io.github.willianlds.entity.OrderSale;
import io.github.willianlds.entity.Product;
import io.github.willianlds.enums.StatusOrder;
import io.github.willianlds.exception.OrderNotFoundException;
import io.github.willianlds.exception.RuleBusinessException;
import io.github.willianlds.repository.Clients;
import io.github.willianlds.repository.ItemsOrders;
import io.github.willianlds.repository.Orders;
import io.github.willianlds.repository.Products;
import io.github.willianlds.rest.dto.ItemsOrderDTO;
import io.github.willianlds.rest.dto.OrderDTO;
import io.github.willianlds.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final Orders ordersRepository;
    private final Clients clientsRepository;
    private final Products productsRepository;
    private final ItemsOrders itemsOrdersRepository;

    @Override
    @Transactional
    public OrderSale save(OrderDTO dto) {
        Integer idClient = dto.getClient();
        Client client = clientsRepository
                .findById(idClient)
                .orElseThrow(() -> new RuleBusinessException("Cod client invalid."));

        OrderSale orderSale = new OrderSale();
        orderSale.setTotal(dto.getTotal());
        orderSale.setDateOrder(LocalDate.now());
        orderSale.setClient(client);
        orderSale.setStatus(StatusOrder.REALIZED);

        List<ItemOrder> itemOrders = converterItems(orderSale, dto.getItems());
        ordersRepository.save(orderSale);
        itemsOrdersRepository.saveAll(itemOrders);

        orderSale.setItems(itemOrders);
        return orderSale;
    }

    @Override
    public Optional<OrderSale> getOrderComplete(Integer id) {
        return ordersRepository.findByIdFetchItems(id);
    }

    @Override
    @Transactional
    public void updateStatus(Integer id, StatusOrder statusOrder) {
        ordersRepository.findById(id)
                .map(order -> {
                    order.setStatus(statusOrder);
                    return ordersRepository.save(order);
                }).orElseThrow(() -> new OrderNotFoundException());
    }

    private List<ItemOrder> converterItems(OrderSale orderSale, List<ItemsOrderDTO> items){
        if(items.isEmpty()){
            throw new RuleBusinessException("Is not possible realized an order without items.");
        }

        return items.stream().map(dto -> {
            Integer idProduct = dto.getProduct();
            Product product = productsRepository
                    .findById(idProduct)
                    .orElseThrow(() -> new RuleBusinessException("Invalid product code: " + idProduct));

            ItemOrder itemOrder = new ItemOrder();
            itemOrder.setQuantity(dto.getQuantity());
            itemOrder.setOrderSale(orderSale);
            itemOrder.setProduct(product);
            return itemOrder;
        }).collect(Collectors.toList());
    }
}
