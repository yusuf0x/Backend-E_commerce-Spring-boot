package com.ecommerce.app.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateCartItemQuantityRequest {
    private Long userId;
    private Long  itemId;
    private Integer newQuantity;
}