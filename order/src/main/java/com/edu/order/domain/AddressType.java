package com.edu.order.domain;

import lombok.Getter;

@Getter
public enum AddressType {
    SHIPPING("SHIPPING"), BILLING("BILLING");

    private final String addressTypeName;

    private AddressType(String addressTypeName) {
        this.addressTypeName = addressTypeName;
    }
}
