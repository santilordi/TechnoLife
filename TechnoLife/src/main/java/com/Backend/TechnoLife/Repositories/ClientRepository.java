package com.Backend.TechnoLife.Repositories;

import com.Backend.TechnoLife.Model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository  extends JpaRepository<Client, Long> {
    @Repository
    public interface ClienteRepository extends JpaRepository<Client, Long> {
        Optional<Client> findByEmail(String email);
    }

}
