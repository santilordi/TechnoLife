package com.Backend.TechnoLife.mappers;

import com.Backend.TechnoLife.Dto.OrderDto;
import com.Backend.TechnoLife.Model.Order;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class OrderMapper {

    public static OrderDto toDto(Order order){
        if (order == null){
            return null;
        }
        return new OrderDto(order);
    }
}
