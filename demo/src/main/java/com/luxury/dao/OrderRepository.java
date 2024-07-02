package com.luxury.dao;

import com.luxury.models.CustomUser;
import com.luxury.models.PlacedOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<PlacedOrder, Long> {
    List<PlacedOrder> findByUser(CustomUser user);
}
