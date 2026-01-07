package com.Backend.TechnoLife.mappers;

import com.Backend.TechnoLife.Dto.ProductDto;
import com.Backend.TechnoLife.Model.Product;

public class ProductMapper {

    private ProductMapper() {}

    public static ProductDto toDto(Product product) {
        if (product == null) return null;

        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCategory(),
                product.getStock()
        );
    }

    public static Product toEntity(ProductDto dto) {
        if (dto == null) return null;

        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getNombre());
        product.setDescription(dto.getDescripcion());
        product.setPrice(dto.getPrecio());
        product.setCategory(dto.getCategoria());
        product.setStock(dto.getStock());

        return product;
    }
}

