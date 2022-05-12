package io.github.willianlds.rest.controller;

import io.github.willianlds.entity.ItemOrder;
import io.github.willianlds.entity.OrderSale;
import io.github.willianlds.enums.StatusOrder;
import io.github.willianlds.rest.dto.UpdatedStatusOrderDTO;
import io.github.willianlds.rest.dto.InfoItemOrderDTO;
import io.github.willianlds.rest.dto.InfoOrderDTO;
import io.github.willianlds.rest.dto.OrderDTO;
import io.github.willianlds.service.OrderService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping(value = "/save")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Save an order")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Order saved successfully"),
            @ApiResponse(code = 400, message = "Error in validation"),
    })
    public Integer save(@RequestBody OrderDTO dto){
        OrderSale orderSale = service.save(dto);
        return orderSale.getId();
    }

    @GetMapping("/searchOrder/{id}")
    @ApiOperation("Show a order")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Order found successfully"),
            @ApiResponse(code = 404, message = "Order not found"),
    })
    public InfoOrderDTO getById(@PathVariable("id") Integer id){
        return service.getOrderComplete(id)
                .map(p -> converter(p))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
    }

    @PatchMapping("/updateStatus/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Update status a order")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Order updated"),
            @ApiResponse(code = 404, message = "Order not found"),
    })
    public void updateStatus(@PathVariable("id") Integer id, @RequestBody UpdatedStatusOrderDTO dto){
        String newStatus = dto.getNewStatus();
        service.updateStatus(id, StatusOrder.valueOf(newStatus));
    }

    public InfoOrderDTO converter(OrderSale orderSale){
        return InfoOrderDTO.builder()
                .cod(orderSale.getId())
                .dateOrder(orderSale.getDateOrder().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .cpf(orderSale.getClient().getCpf())
                .nameClient(orderSale.getClient().getName())
                .totalOrder(orderSale.getTotal())
                .status(orderSale.getStatus().name())
                .items(converter(orderSale.getItems()))
                .build();
    }

    private List<InfoItemOrderDTO> converter(List<ItemOrder> items){
        if(CollectionUtils.isEmpty(items)){
            return Collections.emptyList();
        }

        return items.stream().map(itemOrder -> InfoItemOrderDTO
                .builder().descriptionProduct(itemOrder.getProduct().getDescription())
                .priceUnitary(itemOrder.getProduct().getPrice())
                .quantity(itemOrder.getQuantity())
                .build()
        ).collect(Collectors.toList());
    }
}
