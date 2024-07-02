package com.luxury.dao;

import com.luxury.models.ProductVariants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductVariantsRepo extends JpaRepository<ProductVariants,Long> {

    @Query(value = "SELECT * FROM product_variants where product_id=?1"  , nativeQuery = true)
    List<ProductVariants> getAllVariantsByProductId(Long productId);
    ProductVariants findFirstByProductId(long productId);


}
