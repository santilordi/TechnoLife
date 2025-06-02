package com.Backend.TechnoLife.Services;

import com.Backend.TechnoLife.Model.Client;
import com.Backend.TechnoLife.Repositories.ClientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private ClientRepository repoClient;

    @Autowired
    public ClientService(ClientRepository repoClient) {
        this.repoClient = repoClient;
    }

    //CRUD
    public List<Client> obtenerClients(){
        return repoClient.findAll();
    }

    public Client obtenerClientById(Long id){
        return repoClient.findById(id).orElse(null);
    }

    public Client obtenerClientPorEmail(String email) {
        return (Client) repoClient.findByEmail(email).orElse(null);
    }


    @Transactional
    public Client guardarClient (Client client){
        return repoClient.save(client);
    }

    @Transactional
    public Client actualizarClient (Long id, Client datos){
        Client c = obtenerClientById(id);
        if (c != null){
            c.setName(datos.getName());
            c.setLastName(datos.getLastName());
            c.setEmail(datos.getEmail());
            return repoClient.save(c);
        }
        return null;
    }

}
