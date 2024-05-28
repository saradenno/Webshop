package com.luxury.controller;


import com.luxury.dao.ProductDAO;
import com.luxury.dto.ProductDTO;
import com.luxury.models.Product;
import com.luxury.models.ProductVariants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/products")
public class ProductController {

    private final ProductDAO productDAO;

    public ProductController(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts(){
        return ResponseEntity.ok(this.productDAO.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id){
        return ResponseEntity.ok(this.productDAO.getProductById(id));
    }

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody ProductDTO productDTO){
        this.productDAO.createProduct(productDTO);
        return ResponseEntity.ok("Created a product");
    }
}
