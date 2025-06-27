package com.Backend.TechnoLife.Dto;

import com.Backend.TechnoLife.Model.Client;

public class ClientDto {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String rol;

    public ClientDto (){}

    public ClientDto(Client client) {
        this.id = client.getId();
        this.nombre = client.getName();
        this.apellido = client.getLastName();
        this.email = client.getEmail();
        this.rol = client.getRol() != null ? client.getRol().name() : null;
    }

    // getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
