import com.luxury.controller.ProductController;
import com.luxury.dao.ProductDAO;
import com.luxury.dto.ProductDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
public class ProductControllerTest {
    @Mock
    private ProductDAO productDAO;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testCreateProduct() {
        ProductDTO product = new ProductDTO();
        product.setId(1L);
        product.setName("New Product");

        doNothing().when(productDAO).createProduct(product);

        ResponseEntity<String> response = productController.createProduct(product);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Created a product", response.getBody());
        verify(productDAO, times(1)).createProduct(product);
    }
}
