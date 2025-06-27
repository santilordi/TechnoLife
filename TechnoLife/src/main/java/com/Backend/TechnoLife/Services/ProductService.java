package com.Backend.TechnoLife.Services;

import com.Backend.TechnoLife.Dto.ProductDto;
import com.Backend.TechnoLife.Model.Product;
import com.Backend.TechnoLife.Repositories.ProductRepository;
import com.Backend.TechnoLife.Dto.ProductDto;
import com.Backend.TechnoLife.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Convertir entidad a DTO
    private ProductDto toDTO(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setNombre(product.getName());
        dto.setDescripcion(product.getDescription());
        dto.setPrecio(product.getPrice());
        dto.setCategoria(product.getCategory());
        dto.setStock(product.getStock());
        return dto;
    }

    // Convertir DTO a entidad
    private Product toEntity(ProductDto dto) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getNombre());
        product.setDescription(dto.getDescripcion());
        product.setPrice(dto.getPrecio());
        product.setCategory(dto.getCategoria());
        product.setStock(dto.getStock());
        return product;
    }

    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ProductDto saveProduct(ProductDto dto) {
        Product product = toEntity(dto);
        Product saved = productRepository.save(product);
        return toDTO(saved);
    }
}