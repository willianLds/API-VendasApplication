package io.github.willianlds.repository;

import io.github.willianlds.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Products extends JpaRepository<Product, Integer> {
}
