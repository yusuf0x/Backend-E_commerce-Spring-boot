package com.ecommerce.app.services;

import com.ecommerce.app.repositories.HomeCarouselRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HomeCarouselService {
    private final HomeCarouselRepository homeCarouselRepository;
    @Autowired
    public HomeCarouselService(HomeCarouselRepository homeCarouselRepository) {
        this.homeCarouselRepository = homeCarouselRepository;
    }



}
