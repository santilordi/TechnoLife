package com.Backend.TechnoLife.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private String categoria;
    private int stock;
}