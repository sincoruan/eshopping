package com.edu.account.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


public class Order {
    Long id;
    long userid;
    String orderStatus;

    private String shipAddress;

    private String paymentMethod;
}
