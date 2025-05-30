package com.Backend.TechnoLife.Services;

import com.Backend.TechnoLife.Model.Order;
import com.Backend.TechnoLife.Repositories.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private OrderRepository repoOrder;

    @Autowired
    public OrderService (OrderRepository repoOrder){
        this.repoOrder = repoOrder;
    }

    //CRUD
    public List<Order> obtenerOrdenes(){
        return repoOrder.findAll();
    }

    public Order obtenerOrdenesById(long id){
        return repoOrder.findById(id).orElse(null);
    }

    public List<Order> obtenerClientOrders (long clientID){
        return repoOrder.findByClientID(clientID);
    }


}
