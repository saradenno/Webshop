import com.luxury.controller.ProductController;
import com.luxury.dao.ProductVariantsRepo;
import com.luxury.models.ProductVariants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class TestProductController {

    @Mock
    private ProductVariantsRepo productVariantsRepo;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDeleteVariant_Success() {
        // Arrange
        Long variantId = 1L;
        ProductVariants variant = new ProductVariants();
        when(productVariantsRepo.findById(variantId)).thenReturn(Optional.of(variant));

        // Act
        ResponseEntity<?> response = productController.deleteVariant(variantId);

        // Assert
        verify(productVariantsRepo, times(1)).delete(variant);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Collections.singletonMap("message", "Variant deleted successfully."), response.getBody());
    }

    @Test
    public void testDeleteVariant_NotFound() {
        // Arrange
        Long variantId = 1L;
        when(productVariantsRepo.findById(variantId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = productController.deleteVariant(variantId);

        // Assert
        verify(productVariantsRepo, times(0)).delete(any(ProductVariants.class));
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(Collections.singletonMap("message", "Variant not found."), response.getBody());
    }
}
