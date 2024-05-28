package com.luxury;

import com.luxury.controller.AdminController;
import com.luxury.dao.ProductDAO;
import com.luxury.dao.ProductRepository;
import com.luxury.dao.ProductVariantsRepo;
import com.luxury.dto.ProductDTO;
import com.luxury.dto.ProductVariantsDTO;
import com.luxury.models.Product;
import com.luxury.models.ProductVariants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public record AdminControllerTest() {
    @Mock
    private static ProductDAO productDAO;

    @Mock
    private static ProductRepository productRepository;

    @Mock
    private static ProductVariantsRepo productVariantsRepo;

    @InjectMocks
    private static AdminController adminController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testSaveVariant() {
        // Arrange
        ProductVariantsDTO productVariantsDTO = new ProductVariantsDTO();
        productVariantsDTO.setColor("Red");
        productVariantsDTO.setSize(36);
        productVariantsDTO.setStock(10);
        productVariantsDTO.setPrice(100);
        productVariantsDTO.setProductId(1L);

        Product product = new Product();
        product.setId(1L);

        when(productRepository.findById(productVariantsDTO.getProductId())).thenReturn(Optional.of(product));
        when(productVariantsRepo.save(any(ProductVariants.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        ResponseEntity<ProductVariants> response = adminController.saveVariant(productVariantsDTO);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        ProductVariants savedVariant = response.getBody();
        assertEquals(productVariantsDTO.getColor(), savedVariant.getColor());
        assertEquals(productVariantsDTO.getSize(), savedVariant.getSize());
        assertEquals(productVariantsDTO.getStock(), savedVariant.getStock());
        assertEquals(productVariantsDTO.getPrice(), savedVariant.getPrice());
        assertEquals(product.getId(), savedVariant.getProduct().getId());

        verify(productRepository, times(1)).findById(productVariantsDTO.getProductId());
        verify(productVariantsRepo, times(1)).save(any(ProductVariants.class));
    }


    @Test
    public void testUpdateProduct() {
        // Arrange
        ProductVariantsDTO variantDTO = new ProductVariantsDTO();
        variantDTO.setId(1L);
        variantDTO.setSize(38);
        variantDTO.setStock(10);
        variantDTO.setPrice(99);
        variantDTO.setColor("Red");

        List<ProductVariantsDTO> variantDTOList = new ArrayList<>();
        variantDTOList.add(variantDTO);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setName("Updated Product");
        productDTO.setPrice(199.99);
        productDTO.setDescription("Updated description");
        productDTO.setProdctVariantsList(variantDTOList); // Initialize the list with mock data

        Product updatedProduct = new Product();
        updatedProduct.setId(1L);
        updatedProduct.setName("Updated Product");
        updatedProduct.setPrice(199.99);
        updatedProduct.setDescription("Updated description");

        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        // Act
        ResponseEntity<Product> response = adminController.updateProduct(productDTO);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Updated Product", response.getBody().getName());
        verify(productRepository, times(1)).save(any(Product.class));
    }


    @Test
    public void testSaveProduct() {
        // Arrange
        ProductVariantsDTO variantDTO = new ProductVariantsDTO();
        variantDTO.setSize(38);
        variantDTO.setColor("Red");
        variantDTO.setStock(10);
        variantDTO.setPrice(99);

        List<ProductVariantsDTO> variantDTOList = new ArrayList<>();
        variantDTOList.add(variantDTO);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("New Product");
        productDTO.setPrice(199.99);
        productDTO.setDescription("New product description");
        productDTO.setImgURL("http://example.com/image.jpg");
        productDTO.setGroupset("Shimano");
        productDTO.setMaterial("Carbon");
        productDTO.setWheels("Alloy");
        productDTO.setProdctVariantsList(variantDTOList);

        Product savedProduct = new Product();
        savedProduct.setId(1L);
        savedProduct.setName("New Product");
        savedProduct.setPrice(199.99);
        savedProduct.setDescription("New product description");
        savedProduct.setImgURL("http://example.com/image.jpg");
        savedProduct.setGroupset("Shimano");
        savedProduct.setMaterial("Carbon");
        savedProduct.setWheels("Alloy");

        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        // Act
        ResponseEntity<Product> response = adminController.saveProduct(productDTO);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("New Product", response.getBody().getName());
        assertEquals(199.99, response.getBody().getPrice());
        verify(productRepository, times(1)).save(any(Product.class));
        verify(productVariantsRepo, times(1)).save(any(ProductVariants.class));
    }
}
