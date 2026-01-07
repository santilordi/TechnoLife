package com.Backend.TechnoLife.Dto;

import com.Backend.TechnoLife.Model.Client;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String rol;

    public ClientDto(Client client) {
        this.id = client.getId();
        this.nombre = client.getName();
        this.apellido = client.getLastName();
        this.email = client.getEmail();
        this.rol = client.getRol() != null ? client.getRol().name() : null;
    }
}
