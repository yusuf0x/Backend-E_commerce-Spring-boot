package com.ecommerce.app.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateProduct implements Serializable {
    private String codeProduct;
    private String status;
    private String title;
    private String slug;
    private String description;
    private int quantity;
    private int sold;
    private BigDecimal price;
    private BigDecimal priceAfterDiscount;
    private List<String> colors;
    private String imageCover;
//    private List<String> images;
    private Long categoryId;
    private List<Long> subcategoryIds;
    private Long brandId;
    private Double ratingsAverage;
    private int ratingsQuantity;
}