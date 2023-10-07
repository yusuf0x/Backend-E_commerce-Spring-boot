package com.ecommerce.app.services;

import com.ecommerce.app.exceptions.CouponNotFoundException;
import com.ecommerce.app.models.Coupon;
import com.ecommerce.app.repositories.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CouponService {

    private final CouponRepository couponRepository;

    @Autowired
    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    public Coupon getCouponById(Long id) {
        return couponRepository.findById(id).orElseThrow(
                () -> new CouponNotFoundException("Coupon Not FOund with id = "+id)
        );
    }

    public Coupon createCoupon(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    public Coupon updateCoupon(Long id, Coupon updateCoupon) {
        Coupon coupon = getCouponById(id);
        coupon.setName(updateCoupon.getName());
        coupon.setDiscount(updateCoupon.getDiscount());
        coupon.setExpire(updateCoupon.getExpire());
        return couponRepository.save(coupon);
    }

    public void deleteCoupon(Long id) {
        Coupon coupon = getCouponById(id);
        couponRepository.delete(coupon);
    }
}
