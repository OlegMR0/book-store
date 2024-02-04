package com.example.bookstore.repository.order;

import com.example.bookstore.model.Order;
import com.example.bookstore.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o INNER JOIN FETCH o.orderItems WHERE o.id IN :orderIds")
    List<Order> findAllByUserIds(List<Long> orderIds);

    @Query("SELECT o.id FROM Order o WHERE o.user = :user")
    List<Long> findAllIdsByUser(User user, Pageable pageable);
}
