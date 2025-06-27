package com.Backend.TechnoLife.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("nombre")
    @Column(nullable = false)
    private String name;

    @JsonProperty("apellido")
    @Column(nullable = false)
    private String lastName;

    @JsonProperty("email")
    @Column(nullable = false)
    private String email;

    @JsonProperty("password")
    @Column(nullable = false)
    private String password;

    @JsonProperty("rol")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClientStatus rol;

    public Client() {}

    public Client(Long id, String name, String lastName, String email, String password, ClientStatus rol) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.rol = rol;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ClientStatus getRol() {
        return rol;
    }

    public void setRol(ClientStatus rol) {
        this.rol = rol;
    }
}
