package com.edu.product.repository;

import com.edu.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
//    List<Product> findBySeller(User seller);
//    List<Product> findBySellerAndEnable(User seller, Boolean enable);
//    List<Product> findByEnable(Boolean enable);
    @Modifying
    @Transactional
    @Query(value="update product set count=count + ?1 where id=?2",nativeQuery = true)
    void addProductById(int count,long prodId);
    @Modifying
    @Transactional
    @Query(value="update product set count=count - ?1 where id=?2",nativeQuery = true)
    void minusProductById(int count,long prodId);
    @Query
    Product findProductById(long prodId);

}
