package com.edu.order.repository;

import com.edu.order.domain.Order;
import com.edu.order.domain.UserOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserOrderDetailRepository extends JpaRepository<UserOrderDetail, Long> {

    @Query(value="select * from user_order_detail where order_id=?1",nativeQuery = true)
    List<UserOrderDetail> getUserOrderDetailList(long orderid);
//    @Query(value = "select distinct o from UserOrder o , UserOrderDetail ud where ud.product.seller = ?1 ")
//    List<Order> findBySeller(User seller);
}
