package com.ecommerce.app.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateReview {
    private String title;
    private Double ratings;
    private Long userId;
//    private Long productId;
}
