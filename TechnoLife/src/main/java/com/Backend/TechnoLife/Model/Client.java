package com.Backend.TechnoLife.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
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


}
