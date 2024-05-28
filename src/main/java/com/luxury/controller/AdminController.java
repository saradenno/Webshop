package com.luxury.controller;

import com.luxury.config.JWTUtil;
import com.luxury.dao.AdminRepository;
import com.luxury.dao.ProductDAO;
import com.luxury.dao.ProductRepository;
import com.luxury.dao.ProductVariantsRepo;
import com.luxury.dto.AuthenticationDTO;
import com.luxury.dto.LoginResponse;
import com.luxury.dto.ProductDTO;
import com.luxury.dto.ProductVariantsDTO;
import com.luxury.models.Admin;
import com.luxury.models.Product;
import com.luxury.models.ProductVariants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

/**
 * @author Sarah
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/admin")
public class AdminController {

    private final AuthenticationManager authManager;
    private final JWTUtil jwtUtil;
    private final AdminRepository adminRepository;
    private final ProductDAO productDAO;
    private final ProductVariantsRepo productVariantsRepo;
    private final ProductRepository productRepository;


    public AdminController(AuthenticationManager authManager, JWTUtil jwtUtil, AdminRepository adminRepository, ProductDAO productDAO, ProductVariantsRepo productVariantsRepo, ProductRepository productRepository) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.adminRepository = adminRepository;
        this.productDAO = productDAO;
        this.productVariantsRepo = productVariantsRepo;
        this.productRepository = productRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody AuthenticationDTO body) {
        try {
            System.out.println("Logged admin runs");
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(body.email, body.password);

            Authentication auth = authManager.authenticate(authInputToken);

            System.out.println("Admin "+auth.getAuthorities());

            // Check if the authenticated user has the role ADMIN
            boolean isAdmin = auth.getAuthorities().stream()
                    .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));

            if (!isAdmin) {
                throw new ResponseStatusException(
                        HttpStatus.FORBIDDEN, "User is not authorized as admin"
                );
            }

            String token = jwtUtil.generateToken(body.email);

            Admin admin = adminRepository.findByEmail(body.email);
            LoginResponse loginResponse = new LoginResponse(admin.getEmail(), token);

            return ResponseEntity.ok(loginResponse);

        } catch (Exception authExc) {
            System.out.println(authExc);
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "No valid credentials"
            );
        }
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getAllProducts(){
        return ResponseEntity.ok(this.productDAO.getAllProducts());
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id){
        return ResponseEntity.ok(this.productDAO.getProductById(id));
    }

    @PostMapping("/saveVariant")
    public ResponseEntity<ProductVariants> saveVariant(@RequestBody ProductVariantsDTO productVariantsDTO){
        ProductVariants productVariants=new ProductVariants();
        productVariants.setColor(productVariantsDTO.getColor());
        productVariants.setSize(productVariantsDTO.getSize());
        productVariants.setStock(productVariantsDTO.getStock());
        productVariants.setPrice(productVariantsDTO.getPrice());

        Optional<Product> productOptional = productRepository.findById(productVariantsDTO.getProductId());
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            productVariants.setProduct(product);
        }
        else{
            throw new IllegalStateException("product is not found");
        }
        return ResponseEntity.ok(productVariantsRepo.save(productVariants));
    }



    @PutMapping("updateProduct")
    public ResponseEntity<Product> updateProduct(@RequestBody ProductDTO productDTO) {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        product.setImgURL(productDTO.getImgURL());
        product.setGroupset(productDTO.getGroupset());
        product.setMaterial(productDTO.getMaterial());
        product.setWheels(productDTO.getWheels());

        for(ProductVariantsDTO productVariants: productDTO.getProdctVariantsList()){
            ProductVariants p=new ProductVariants();
            p.setId(productVariants.getId());
            p.setStock(productVariants.getStock());
            p.setPrice(productVariants.getPrice());
            p.setSize(productVariants.getSize());
            p.setColor(productVariants.getColor());
            p.setProduct(product);
            productVariantsRepo.save(p);
        }

        return ResponseEntity.ok(productRepository.save(product));
    }

    @PostMapping("/saveProduct")
    public ResponseEntity<Product> saveProduct(@RequestBody ProductDTO productDTO){
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        product.setImgURL(productDTO.getImgURL());
        product.setGroupset(productDTO.getGroupset());
        product.setMaterial(productDTO.getMaterial());

        Product saveProduct = productRepository.save(product);

        for(ProductVariantsDTO productVariantsDTO:productDTO.getProdctVariantsList()){
            ProductVariants p = new ProductVariants();
            p.setSize(productVariantsDTO.getSize());
            p.setStock(productVariantsDTO.getStock());
            p.setColor(productVariantsDTO.getColor());
            p.setProduct(saveProduct);
            productVariantsRepo.save(p);
        }
        return ResponseEntity.ok(saveProduct);
    }

}
