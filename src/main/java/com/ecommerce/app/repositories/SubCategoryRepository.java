package com.ecommerce.app.repositories;

import com.ecommerce.app.models.Category;
import com.ecommerce.app.models.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory,Long> {
    List<SubCategory> findByCategory(Category category);
}
