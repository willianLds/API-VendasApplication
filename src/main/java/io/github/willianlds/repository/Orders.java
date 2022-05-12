package io.github.willianlds.repository;

import io.github.willianlds.entity.Client;
import io.github.willianlds.entity.OrderSale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface Orders extends JpaRepository<OrderSale, Integer> {
    Set<OrderSale> findByClient(Client client);

    @Query(value = " select p from OrderSale p left join fetch p.items where p.id = :id ")
    Optional<OrderSale> findByIdFetchItems(@Param("id") Integer id);

}
