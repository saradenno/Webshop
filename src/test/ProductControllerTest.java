
import com.luxury.controller.ProductController;
import com.luxury.dao.ProductDAO;
import com.luxury.dto.ProductDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

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
    public void testGetAllProducts() {
        ProductDTO product1 = new ProductDTO();
        product1.setId(1L);
        product1.setName("Product 1");

        ProductDTO product2 = new ProductDTO();
        product2.setId(2L);
        product2.setName("Product 2");

        when(productDAO.getAllProducts()).thenReturn(Arrays.asList(product1, product2));

        ResponseEntity<List<ProductDTO>> response = productController.getAllProducts();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        verify(productDAO, times(1)).getAllProducts();
    }

    @Test
    public void testGetProductById() {
        ProductDTO product = new ProductDTO();
        product.setId(1L);
        product.setName("Product 1");

        when(productDAO.getProductById(1L)).thenReturn(product);

        ResponseEntity<ProductDTO> response = productController.getProductById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Product 1", response.getBody().getName());
        verify(productDAO, times(1)).getProductById(1L);
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

