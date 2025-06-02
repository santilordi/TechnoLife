package com.Backend.TechnoLife.Repositories;

import com.Backend.TechnoLife.Model.Product;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
