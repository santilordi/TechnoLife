package com.Backend.TechnoLife.Repositories;

import com.Backend.TechnoLife.Model.Client;
import com.Backend.TechnoLife.Model.Product;
import com.Backend.TechnoLife.Model.ShoppingCartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCartItem, Integer> {
    List<ShoppingCartItem> findByClient (Client client);

    Optional<ShoppingCartItem> findByClientAndProduct(Client client, Product product);

    void deleteByClient (Client client);
}
