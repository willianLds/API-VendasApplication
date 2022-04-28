package io.github.willianlds.repository;

import io.github.willianlds.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface Clientes extends JpaRepository<Cliente, Integer> {

    @Query(value = " select c from Cliente c where nome like %:nome% ")
    List<Cliente> findByName (@Param("nome") String nome);

    @Modifying
    @Query(" delete from Cliente c where c.nome = :nome ")
    void deleteByName(@Param("nome") String nome);

    @Query(" select c from Cliente c left join fetch c.pedidos where c.id = :id ")
    Cliente findClientFetchPedidos(@Param("id") Integer id);
}
