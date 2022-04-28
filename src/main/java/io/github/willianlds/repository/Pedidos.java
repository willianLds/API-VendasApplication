package io.github.willianlds.repository;

import io.github.willianlds.entity.Cliente;
import io.github.willianlds.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface Pedidos extends JpaRepository<Pedido, Integer> {
    Set<Pedido> findByCliente(Cliente cliente);

    @Query(value = " select p from Pedido p left join fetch p.items where p.id = :id ")
    Optional<Pedido> findByIdFetchItems(@Param("id") Integer id);

}
