package com.ecommerce.app.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class ShippingAddress {
    @Column(name = "details")
    private String details;

    @Column(name = "phone")
    private String phone;


    private String street;
    private String country;


    @Column(name = "city")
    private String city;

    @Column(name = "postal_code")
    private String postalCode;
}