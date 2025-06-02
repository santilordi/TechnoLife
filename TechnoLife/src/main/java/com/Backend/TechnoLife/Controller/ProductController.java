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
}