package com.edu.order.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @OneToOne
    User user;
    @NotEmpty
    String addressName;
    @NotEmpty
    String streetAddress1;
    @NotEmpty
    String city;
    @NotEmpty
    String state;
    @NotEmpty
    String zipCode;
    @NotEmpty
    String phoneNumber;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    AddressType addressType;

}
