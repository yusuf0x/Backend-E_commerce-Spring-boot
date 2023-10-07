package com.ecommerce.app.models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codeProduct;

    private String status="active";

//    @Column( length = 100)
    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, unique = true)
//    @Column(unique = true)
    private String slug;

    @Column(nullable = false, length = 500)
//    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private int sold;

    @Column(nullable = false)
    private BigDecimal price;

    @Column
    private BigDecimal priceAfterDiscount;

    @ElementCollection
    private List<String> colors;

    @Column(nullable = false)
    private String imageCover;

    @ElementCollection
    private List<String> images;

    @ManyToOne
    @JoinColumn(name = "category_id",nullable = false)
    private Category category;

    @ManyToMany
    @JoinTable(
            name = "product_subcategory",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "subcategory_id")
    )
    private List<SubCategory> subcategories;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @Column
    private Double ratingsAverage;

    @Column
    private int ratingsQuantity;

    @OneToMany(mappedBy = "product")
    private List<Review> reviews;

}

