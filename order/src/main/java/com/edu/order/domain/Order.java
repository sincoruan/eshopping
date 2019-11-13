package com.edu.order.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    long userid;
    @OneToMany(cascade = CascadeType.ALL)
    List<UserOrderDetail> userOrderDetailList;
    String orderStatus;

    private String shipAddress;

    private String paymentMethod;
}
