package com.edu.product.repository;

import com.edu.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
//    List<Product> findBySeller(User seller);
//    List<Product> findBySellerAndEnable(User seller, Boolean enable);
//    List<Product> findByEnable(Boolean enable);
    @Query(value="update product set count=?1 where id=?2",nativeQuery = true)
    Product updateProductById(long prodId,int count);
}
