package dev.adovgapp.advogapp.repositories;

import dev.adovgapp.advogapp.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
}