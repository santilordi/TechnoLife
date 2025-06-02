package com.Backend.TechnoLife.Controller;

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

    // Obtener todos los clientes
    @GetMapping
    public List<Client> obtenerClientes() {
        return clientService.obtenerClients();
    }

    // Obtener cliente por ID
    @GetMapping("/{id}")
    public ResponseEntity<Client> obtenerClientePorId(@PathVariable Long id) {
        Client cliente = clientService.obtenerClientById(id);
        if (cliente != null) {
            return ResponseEntity.ok(cliente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Crear nuevo cliente
    @PostMapping("/register")
    public ResponseEntity<Client> crearCliente(@RequestBody Client cliente) {
        Client nuevo = clientService.guardarClient(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    // Actualizar cliente existente
    @PutMapping("/{id}")
    public ResponseEntity<Client> actualizarCliente(@PathVariable Long id, @RequestBody Client datos) {
        Client actualizado = clientService.actualizarClient(id, datos);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Iniciar sesión por email (retorna el cliente)
    @PostMapping("/login")
    public ResponseEntity<Client> login(@RequestBody LoginDto loginDto) {
        Client cliente = clientService.obtenerClientPorEmail(loginDto.getEmail());
        if (cliente != null && cliente.getPassword().equals(loginDto.getPassword()) ) {
            return ResponseEntity.ok(cliente);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
