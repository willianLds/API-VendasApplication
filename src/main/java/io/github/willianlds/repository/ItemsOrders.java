package io.github.willianlds.repository;

import io.github.willianlds.entity.ItemOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemsOrders extends JpaRepository<ItemOrder, Integer> {
}
