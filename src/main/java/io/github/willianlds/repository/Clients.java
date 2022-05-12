package io.github.willianlds.repository;

import io.github.willianlds.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface Clients extends JpaRepository<Client, Integer> {

    @Query(value = " select c from Client c where name like %:name% ")
    List<Client> findByName (@Param("name") String name);

    @Modifying
    @Query(" delete from Client c where c.name = :name ")
    void deleteByName(@Param("name") String name);

    @Query(" select c from Client c left join fetch c.orders where c.id = :id ")
    Client findClientFetchOrders(@Param("id") Integer id);
}
