package com.Backend.TechnoLife.Repositories;

import com.Backend.TechnoLife.Model.Client;
import com.Backend.TechnoLife.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query ("SELECT o FROM Order o WHERE o.client.id = :userId")
    List<Order> findByClientID (@Param("userId") Long userId);
}
