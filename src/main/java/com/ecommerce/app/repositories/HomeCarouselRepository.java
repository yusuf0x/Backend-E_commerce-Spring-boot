package com.ecommerce.app.repositories;
import com.ecommerce.app.models.HomeCarousel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomeCarouselRepository extends JpaRepository<HomeCarousel,Long> {

}
