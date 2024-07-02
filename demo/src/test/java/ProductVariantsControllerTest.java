//package com.luxury.controller;
//
//import com.luxury.dao.ProductVariantsRepo;
//import com.luxury.models.ProductVariants;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//public class ProductVariantsControllerTest {
//
//    @Mock
//    private ProductVariantsRepo productVariantsRepo;
//
//    @InjectMocks
//    private ProductVariantsController productVariantsController;
//
//    @Test
//    public void testCheckVariantStock_VariantExists_InStock() {
//        // Arrange
//        Long variantId = 1L;
//        ProductVariants variant = new ProductVariants();
//        variant.setId(variantId);
//        variant.setStock(10);
//
//        when(productVariantsRepo.findById(variantId)).thenReturn(Optional.of(variant));
//
//        // Act
//        ResponseEntity<Boolean> response = productVariantsController.checkVariantStock(variantId);
//
//        // Assert
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(true, response.getBody());
//    }
//
//    @Test
//    public void testCheckVariantStock_VariantExists_OutOfStock() {
//        // Arrange
//        Long variantId = 2L;
//        ProductVariants variant = new ProductVariants();
//        variant.setId(variantId);
//        variant.setStock(0);
//
//        when(productVariantsRepo.findById(variantId)).thenReturn(Optional.of(variant));
//
//        // Act
//        ResponseEntity<Boolean> response = productVariantsController.checkVariantStock(variantId);
//
//        // Assert
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(false, response.getBody());
//    }
//
//    @Test
//    public void testCheckVariantStock_VariantDoesNotExist() {
//        // Arrange
//        Long variantId = 3L;
//
//        when(productVariantsRepo.findById(variantId)).thenReturn(Optional.empty());
//
//        // Act
//        ResponseEntity<Boolean> response = productVariantsController.checkVariantStock(variantId);
//
//        // Assert
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//        assertEquals(false, response.getBody());
//    }
//}
