package com.ecommerce.app.repositories;

import com.ecommerce.app.models.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
    Coupon findByNameAndExpireAfter(String couponCode, Date date);
}