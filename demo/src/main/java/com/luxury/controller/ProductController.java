package com.luxury.controller;


import com.luxury.dao.ProductDAO;
import com.luxury.dao.ProductVariantsRepo;
import com.luxury.dto.ProductDTO;
import com.luxury.models.Product;
import com.luxury.models.ProductVariants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/products")
public class ProductController {

    private final ProductDAO productDAO;
    private final ProductVariantsRepo productVariantsRepo;


    public ProductController(ProductDAO productDAO, ProductVariantsRepo productVariantsRepo) {
        this.productDAO = productDAO;
        this.productVariantsRepo = productVariantsRepo;
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
    public ResponseEntity<?> deleteVariant(@PathVariable Long id) {
        Optional<ProductVariants> productVariant = productVariantsRepo.findById(id);
        if (productVariant.isPresent()) {
            productVariantsRepo.delete(productVariant.get());
            return ResponseEntity.ok().body(Collections.singletonMap("message", "Variant deleted successfully."));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("message", "Variant not found."));
        }
    }
}
