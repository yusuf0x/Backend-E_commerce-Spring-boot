package com.ecommerce.app.payload.request;

import com.ecommerce.app.models.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private String receipt;
    private BigDecimal amount;
    private Long uidPerson;
    private List<Product> products;
}
