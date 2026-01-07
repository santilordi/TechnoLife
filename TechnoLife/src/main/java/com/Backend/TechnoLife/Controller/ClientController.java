package com.Backend.TechnoLife.Controller;

import com.Backend.TechnoLife.Dto.ClientDto;
import com.Backend.TechnoLife.Dto.LoginDto;
import com.Backend.TechnoLife.Model.Client;
import com.Backend.TechnoLife.Services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public List<ClientDto> obtenerClientes() {
        return clientService.obtenerClients();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> obtenerClientePorId(@PathVariable Long id) {
        ClientDto cliente = clientService.obtenerClientById(id);
        return cliente != null
                ? ResponseEntity.ok(cliente)
                : ResponseEntity.notFound().build();
    }

    @PostMapping("/register")
    public ResponseEntity<ClientDto> crearCliente(@RequestBody Client cliente) {
        ClientDto nuevo = clientService.guardarClient(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDto> actualizarCliente(
            @PathVariable Long id,
            @RequestBody Client datos) {

        ClientDto actualizado = clientService.actualizarClient(id, datos);
        return actualizado != null
                ? ResponseEntity.ok(actualizado)
                : ResponseEntity.notFound().build();
    }

    @PostMapping("/login")
    public ResponseEntity<ClientDto> login(@RequestBody LoginDto loginDto) {
        Client cliente = clientService.obtenerClientPorEmail(loginDto.getEmail());

        if (cliente != null && cliente.getPassword().equals(loginDto.getPassword())) {
            return ResponseEntity.ok(
                    clientService.obtenerClientById(cliente.getId())
            );
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
