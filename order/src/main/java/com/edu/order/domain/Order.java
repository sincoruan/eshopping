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
    @OneToOne
    User buyer;
    @OneToMany(cascade = CascadeType.ALL)
    List<UserOrderDetail> userOrderDetailList;

    long paymentId;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    OrderStatus orderStatus;

    @Type(type = "yes_no")
    @Column(nullable = false)
    Boolean enable;
    LocalDateTime createDateTime;
    LocalDateTime updateDateTime;

}
