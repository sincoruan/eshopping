package com.edu.order.repository;

import com.edu.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserOrderRepository extends JpaRepository<Order, Long> {
    @Query(value = "select * from orders where userid=?1 and order_status='DRAFT'",nativeQuery = true)
    List<Order> findByUserId(long id);

    @Query(value="update orders set order_status = ?1 where orderid=?2 ",nativeQuery = true)
    void updateUserOrderStatusById(String status,long id);
//    @Query(value = "select distinct o from UserOrder o , UserOrderDetail ud where ud.product.seller = ?1 ")
//    List<Order> findBySeller(User seller);
}
