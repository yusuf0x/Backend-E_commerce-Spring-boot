package com.ecommerce.app.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private List<OrderItem> orderItems;

    @Column(name = "tax_price")
    private Double taxPrice;

    @Embedded
    private ShippingAddress shippingAddress;

    @Column(name = "shipping_price")
    private Double shippingPrice;

    @Column(name = "total_order_price")
    private Double totalOrderPrice;

    @Column(name = "payment_method_type")
    private String paymentMethodType;

    @Column(name = "is_paid")
    private Boolean isPaid;

    @Column(name = "paid_at")
    private Date paidAt;

    @Column(name = "is_delivered")
    private Boolean isDelivered;

    @Column(name = "delivered_at")
    private Date deliveredAt;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

}