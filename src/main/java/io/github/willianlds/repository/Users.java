package io.github.willianlds.repository;

import io.github.willianlds.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Users extends JpaRepository<User, Integer> {

    Optional<User> findByUsername (String username);
}
