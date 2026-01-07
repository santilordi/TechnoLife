package com.Backend.TechnoLife.Services;

import com.Backend.TechnoLife.Dto.ClientDto;
import com.Backend.TechnoLife.Model.Client;
import com.Backend.TechnoLife.Model.ClientStatus;
import com.Backend.TechnoLife.Repositories.ClientRepository;
import com.Backend.TechnoLife.mappers.ClientMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private final ClientRepository repoClient;
    private final ClientMapper clientMapper;

    @Autowired
    public ClientService(ClientRepository repoClient, ClientMapper clientMapper) {
        this.repoClient = repoClient;
        this.clientMapper = clientMapper;
    }

    // =====================
    // DTO METHODS (Controller)
    // =====================

    public List<ClientDto> obtenerClients() {
        return repoClient.findAll()
                .stream()
                .map(clientMapper::toDto)
                .toList();
    }

    public ClientDto obtenerClientById(Long id) {
        return repoClient.findById(id)
                .map(clientMapper::toDto)
                .orElse(null);
    }

    // =====================
    // ENTITY METHODS (Business / Security)
    // =====================

    public Client obtenerClientEntityById(Long id) {
        return repoClient.findById(id).orElse(null);
    }

    public Client obtenerClientPorEmail(String email) {
        return repoClient.findByEmail(email).orElse(null);
    }

    // =====================
    // WRITE
    // =====================

    @Transactional
    public ClientDto guardarClient(Client client) {
        if (client.getRol() == null) {
            client.setRol(ClientStatus.USUARIO);
        }
        Client saved = repoClient.save(client);
        return clientMapper.toDto(saved);
    }

    @Transactional
    public ClientDto actualizarClient(Long id, Client datos) {
        Client entity = repoClient.findById(id).orElse(null);
        if (entity == null) return null;

        entity.setName(datos.getName());
        entity.setLastName(datos.getLastName());
        entity.setEmail(datos.getEmail());

        return clientMapper.toDto(repoClient.save(entity));
    }
}
