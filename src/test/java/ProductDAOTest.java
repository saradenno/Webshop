
import com.luxury.dao.ProductDAO;
import com.luxury.dao.ProductRepository;
import com.luxury.dao.ProductVariantsRepo;
import com.luxury.dto.ProductDTO;
import com.luxury.dto.ProductVariantsDTO;
import com.luxury.models.Product;
import com.luxury.models.ProductVariants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class ProductDAOTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductVariantsRepo productVariantsRepo;

    @InjectMocks
    private ProductDAO productDAO;

    @Captor
    private ArgumentCaptor<Product> productCaptor;

    @BeforeEach
    public void setup() {
        productDAO = new ProductDAO(productRepository);
        productDAO.productVariantsRepo = productVariantsRepo;
    }

    @Test
    public void testGetAllProducts() {
        // Arrange
        Product product = new Product("Test Product", 100.0, "Description", "http://example.com/img.png", "Groupset", "Material", "Wheels");
        product.setId(1L);
        List<Product> products = new ArrayList<>();
        products.add(product);

        ProductVariants variant = new ProductVariants();
        variant.setId(1L);
        variant.setColor("Red");
        variant.setSize(36);
        variant.setStock(10);
        variant.setPrice(10.0);
        variant.setProduct(product);
        List<ProductVariants> variants = new ArrayList<>();
        variants.add(variant);

        when(productRepository.findAll()).thenReturn(products);
        when(productVariantsRepo.getAllVariantsByProductId(1L)).thenReturn(variants);

        // Act
        List<ProductDTO> result = productDAO.getAllProducts();

        // Assert
        assertEquals(1, result.size());
        ProductDTO productDTO = result.get(0);
        assertEquals("Test Product", productDTO.getName());
        assertEquals(1, productDTO.getProdctVariantsList().size());
        ProductVariantsDTO variantDTO = productDTO.getProdctVariantsList().get(0);
        assertEquals("Red", variantDTO.getColor());
        assertEquals(36, variantDTO.getSize());
    }

    @Test
    public void testGetProductById() {
        // Arrange
        Product product = new Product("Test Product", 100.0, "Description", "http://example.com/img.png", "Groupset", "Material", "Wheels");
        product.setId(1L);
        ProductVariants variant = new ProductVariants();
        variant.setId(1L);
        variant.setColor("Red");
        variant.setSize(36);
        variant.setStock(10);
        variant.setPrice(10.0);
        variant.setProduct(product);
        List<ProductVariants> variants = new ArrayList<>();
        variants.add(variant);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productVariantsRepo.getAllVariantsByProductId(1L)).thenReturn(variants);

        // Act
        ProductDTO result = productDAO.getProductById(1L);

        // Assert
        assertEquals("Test Product", result.getName());
        assertEquals(1, result.getProdctVariantsList().size());
        ProductVariantsDTO variantDTO = result.getProdctVariantsList().get(0);
        assertEquals("Red", variantDTO.getColor());
        assertEquals(36, variantDTO.getSize());
    }

    @Test
    public void testGetProductById_NotFound() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> productDAO.getProductById(1L));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    public void testCreateProduct_FromProduct() {
        // Arrange
        Product product = new Product("Test Product", 100.0, "Description", "http://example.com/img.png", "Groupset", "Material", "Wheels");

        // Act
        productDAO.createProduct(product);

        // Assert
        verify(productRepository).save(productCaptor.capture());
        Product savedProduct = productCaptor.getValue();
        assertEquals("Test Product", savedProduct.getName());
    }

    @Test
    public void testCreateProduct_FromProductDTO() {
        // Arrange
        ProductVariantsDTO variantDTO = new ProductVariantsDTO();
        variantDTO.setColor("Red");
        variantDTO.setSize(36);
        variantDTO.setStock(10);
        variantDTO.setPrice(10.0);
        List<ProductVariantsDTO> variantsDTO = new ArrayList<>();
        variantsDTO.add(variantDTO);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Test Product");
        productDTO.setPrice(100.0);
        productDTO.setDescription("Description");
        productDTO.setImgURL("http://example.com/img.png");
        productDTO.setGroupset("Groupset");
        productDTO.setMaterial("Material");
        productDTO.setWheels("Wheels");
        productDTO.setProdctVariantsList(variantsDTO);

        // Act
        productDAO.createProduct(productDTO);

        // Assert
        verify(productRepository).save(any(Product.class));
        verify(productVariantsRepo).save(any(ProductVariants.class));
    }


}
