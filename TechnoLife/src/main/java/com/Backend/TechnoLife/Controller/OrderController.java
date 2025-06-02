package com.Backend.TechnoLife.Controller;

import com.Backend.TechnoLife.Dto.OrderDto;
import com.Backend.TechnoLife.Exception.CustomerNotFoundException;
import com.Backend.TechnoLife.Exception.EmptyOrderException;
import com.Backend.TechnoLife.Exception.OrderException;
import com.Backend.TechnoLife.Model.Client;
import com.Backend.TechnoLife.Model.Order;
import com.Backend.TechnoLife.Model.OrderStatus;
import com.Backend.TechnoLife.Services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController (OrderService orderService) {
        this.orderService = orderService;
    }


    @GetMapping("/client")
    public ResponseEntity<List<Order>> listarTodasOrdenes(){

        return ResponseEntity.ok(orderService.obtenerOrdenes());
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Order>> listarOrdenesPorCliente(@PathVariable Long id){

        return ResponseEntity.ok(orderService.obtenerClientOrders(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> obtenerOrderByID(@PathVariable Long id) throws OrderException {
        try{
            Order order = orderService.obtenerOrdenById(id);
            return ResponseEntity.ok(order);
        } catch (OrderException e){
            //Si la orden no se encunetra, devuelve 404 Not Found
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }


    //Crear orden desde el carrito de compras
    @PostMapping("/save")
    public ResponseEntity<OrderDto> crearOrdenDesdeCarrito (@RequestBody OrderDto orderDto){
        try {
            Order nuevaOrden = orderService.createOrderFromShoppingCart(orderDto);
            return new ResponseEntity<>(new OrderDto(nuevaOrden), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error al crear el pedido: " + e.getMessage(), e);
        }
    }


    //ADMIN
    @PutMapping("/{id}/status")
    public ResponseEntity<Order> actualizarEstadoOrden (@PathVariable Long id, @RequestBody OrderStatus newStatus) {
        try {
            Order updatedOrder = orderService.updateOrderStatus(id, newStatus);
            return ResponseEntity.ok(updatedOrder);
        } catch (OrderException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            // Si el OrderStatus no es válido
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    //Para ADMIN o CLIENTE
    @PutMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelarOrden (@PathVariable Long id){
        try {
            orderService.cancelOrder(id);
            return ResponseEntity.noContent().build();
        } catch (OrderException e) {
            throw new RuntimeException(e);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarOrden (@PathVariable Long id){
        try {
            orderService.deleteOrder(id);
            return ResponseEntity.noContent().build();
        } catch (OrderException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar la orden: " + e.getMessage(), e);
        }
    }

    @GetMapping("/dto")
    public ResponseEntity<List<OrderDto>> obtenerHistorialPedidosDTO() {
        List<Order> pedidos = orderService.obtenerOrdenes();
        List<OrderDto> pedidosDTO = pedidos.stream()
                .map(OrderDto::new)
                .toList();
        return ResponseEntity.ok(pedidosDTO);
    }
}
