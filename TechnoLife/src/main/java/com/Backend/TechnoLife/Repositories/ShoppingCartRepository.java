package com.Backend.TechnoLife.Repositories;

import com.Backend.TechnoLife.Model.Client;
import com.Backend.TechnoLife.Model.Product;
import com.Backend.TechnoLife.Model.ShoppingCartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCartItem, Long> {

    List<ShoppingCartItem> findByClient(Client client);

    Optional<ShoppingCartItem> findByClientAndProduct(Client client, Product product);

    void deleteByClient(Client client);
}
