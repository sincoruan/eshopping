package com.edu.product.domain;

import lombok.Getter;

@Getter
public enum OrderStatus {
    DRAFT("DRAFT"),
    PAID("PAID"),
    SHIPPED("SHIPPED"),
    CANCEL("CANCEL");

    private final String orderStatusName;

    private OrderStatus(String orderStatusName) {
        this.orderStatusName = orderStatusName;
    }
}
