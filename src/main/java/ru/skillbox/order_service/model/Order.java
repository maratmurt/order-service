package ru.skillbox.order_service.model;

import lombok.Data;

@Data
public class Order {

    private String product;

    private Integer quantity;

}
