package com.Backend.TechnoLife.Controller;

import com.Backend.TechnoLife.Dto.ProductDto;
import com.Backend.TechnoLife.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping
    public ProductDto addProduct(@RequestBody ProductDto productoDTO) {
        return productService.saveProduct(productoDTO);
    }

    // Actualizar un producto existente
    @PutMapping("/{id}")
    public ProductDto updateProduct(@PathVariable Long id, @RequestBody ProductDto productoDTO) {
        return productService.updateProduct(id, productoDTO);
    }

    // Eliminar un producto
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}