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
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AdminControllerTest {
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

        Product existingProduct = new Product();
        existingProduct.setId(1L);
        existingProduct.setName("Old Product");
        existingProduct.setDescription("Old Description");
        existingProduct.setPrice(99.99);
        existingProduct.setImgURL("http://example.com/old.jpg");
        existingProduct.setGroupset("Old Groupset");
        existingProduct.setMaterial("Old Material");
        existingProduct.setWheels("Old Wheels");

        ProductVariants existingVariant = new ProductVariants();
        existingVariant.setId(1L);
        existingVariant.setSize(40); // Initial size
        existingVariant.setStock(10); // Initial stock
        existingVariant.setPrice(49.99); // Initial price
        existingVariant.setImgURL("http://example.com/old_variant.jpg"); // Initial URL
        existingVariant.setColor("Blue"); // Initial color
        existingVariant.setProduct(existingProduct);

        when(productRepository.findById(productDTO.getId())).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        when(productVariantsRepo.findById(variantDTO.getId())).thenReturn(Optional.of(existingVariant));
        when(productVariantsRepo.save(any(ProductVariants.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        ResponseEntity<Product> response = adminController.updateProduct(productDTO);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Updated Product", response.getBody().getName());
        assertEquals(199.99, response.getBody().getPrice());
        assertEquals("Updated description", response.getBody().getDescription());
        verify(productRepository, times(1)).save(any(Product.class));

        // Verify that the variant was updated correctly
        ArgumentCaptor<ProductVariants> variantCaptor = ArgumentCaptor.forClass(ProductVariants.class);
        verify(productVariantsRepo, times(1)).save(variantCaptor.capture());
        ProductVariants updatedVariant = variantCaptor.getValue();
        assertEquals(variantDTO.getSize(), updatedVariant.getSize());
        assertEquals(variantDTO.getStock(), updatedVariant.getStock());
        assertEquals(variantDTO.getPrice(), updatedVariant.getPrice());
        assertEquals(variantDTO.getImgURL(), updatedVariant.getImgURL());
        assertEquals(variantDTO.getColor(), updatedVariant.getColor());
    }



    @Test
    public void testSaveProductWithMultipleVariants() {
        // Arrange
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Test Product");
        productDTO.setDescription("Test Description");
        productDTO.setPrice(99.99);
        productDTO.setImgURL("http://example.com/test.jpg");
        productDTO.setGroupset("Test Groupset");
        productDTO.setMaterial("Test Material");

        ProductVariantsDTO variantDTO1 = new ProductVariantsDTO();
        variantDTO1.setSize(42);
        variantDTO1.setStock(10);
        variantDTO1.setPrice(49.99);
        variantDTO1.setImgURL("http://example.com/variant1.jpg");
        variantDTO1.setColor("Red");

        ProductVariantsDTO variantDTO2 = new ProductVariantsDTO();
        variantDTO2.setSize(44);
        variantDTO2.setStock(15);
        variantDTO2.setPrice(59.99);
        variantDTO2.setImgURL("http://example.com/variant2.jpg");
        variantDTO2.setColor("Blue");

        List<ProductVariantsDTO> variantsList = new ArrayList<>();
        variantsList.add(variantDTO1);
        variantsList.add(variantDTO2);
        productDTO.setProdctVariantsList(variantsList);

        Product savedProduct = new Product();
        savedProduct.setId(1L);
        savedProduct.setName(productDTO.getName());
        savedProduct.setDescription(productDTO.getDescription());
        savedProduct.setPrice(productDTO.getPrice());
        savedProduct.setImgURL(productDTO.getImgURL());
        savedProduct.setGroupset(productDTO.getGroupset());
        savedProduct.setMaterial(productDTO.getMaterial());

        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        ProductVariants savedVariant1 = new ProductVariants();
        savedVariant1.setId(1L);
        savedVariant1.setSize(variantDTO1.getSize());
        savedVariant1.setStock(variantDTO1.getStock());
        savedVariant1.setPrice(variantDTO1.getPrice());
        savedVariant1.setImgURL(variantDTO1.getImgURL());
        savedVariant1.setColor(variantDTO1.getColor());
        savedVariant1.setProduct(savedProduct);

        ProductVariants savedVariant2 = new ProductVariants();
        savedVariant2.setId(2L);
        savedVariant2.setSize(variantDTO2.getSize());
        savedVariant2.setStock(variantDTO2.getStock());
        savedVariant2.setPrice(variantDTO2.getPrice());
        savedVariant2.setImgURL(variantDTO2.getImgURL());
        savedVariant2.setColor(variantDTO2.getColor());
        savedVariant2.setProduct(savedProduct);

        when(productVariantsRepo.save(any(ProductVariants.class))).thenReturn(savedVariant1).thenReturn(savedVariant2);

        // Act
        ResponseEntity<Product> response = adminController.saveProduct(productDTO);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(savedProduct.getName(), response.getBody().getName());
        assertEquals(savedProduct.getDescription(), response.getBody().getDescription());
        assertEquals(savedProduct.getPrice(), response.getBody().getPrice());
        assertEquals(savedProduct.getImgURL(), response.getBody().getImgURL());
        assertEquals(savedProduct.getGroupset(), response.getBody().getGroupset());
        assertEquals(savedProduct.getMaterial(), response.getBody().getMaterial());

        verify(productRepository, times(1)).save(any(Product.class));
        verify(productVariantsRepo, times(2)).save(any(ProductVariants.class));
    }

    @Test
    public void testUpdateProductAndVariant() {
        // Arrange
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setName("Updated Product");
        productDTO.setDescription("Updated Description");
        productDTO.setPrice(199.99);
        productDTO.setImgURL("http://example.com/updated.jpg");
        productDTO.setGroupset("Updated Groupset");
        productDTO.setMaterial("Updated Material");
        productDTO.setWheels("Updated Wheels");

        ProductVariantsDTO variantDTO = new ProductVariantsDTO();
        variantDTO.setId(1L); // Existing variant ID
        variantDTO.setSize(42);
        variantDTO.setStock(20);
        variantDTO.setPrice(59.99);
        variantDTO.setImgURL("http://example.com/variant.jpg");
        variantDTO.setColor("Red");

        List<ProductVariantsDTO> variantsList = new ArrayList<>();
        variantsList.add(variantDTO);
        productDTO.setProdctVariantsList(variantsList);

        Product existingProduct = new Product();
        existingProduct.setId(1L);
        existingProduct.setName("Old Product");
        existingProduct.setDescription("Old Description");
        existingProduct.setPrice(99.99);
        existingProduct.setImgURL("http://example.com/old.jpg");
        existingProduct.setGroupset("Old Groupset");
        existingProduct.setMaterial("Old Material");
        existingProduct.setWheels("Old Wheels");

        ProductVariants existingVariant = new ProductVariants();
        existingVariant.setId(1L);
        existingVariant.setSize(40); // Initial size
        existingVariant.setStock(10); // Initial stock
        existingVariant.setPrice(49.99); // Initial price
        existingVariant.setImgURL("http://example.com/old_variant.jpg"); // Initial URL
        existingVariant.setColor("Blue"); // Initial color
        existingVariant.setProduct(existingProduct);

        when(productRepository.findById(productDTO.getId())).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(productVariantsRepo.findById(variantDTO.getId())).thenReturn(Optional.of(existingVariant));
        when(productVariantsRepo.save(any(ProductVariants.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        ResponseEntity<Product> response = adminController.updateProduct(productDTO);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Product updatedProduct = response.getBody();
        assertNotNull(updatedProduct);
        assertEquals(productDTO.getName(), updatedProduct.getName());
        assertEquals(productDTO.getDescription(), updatedProduct.getDescription());
        assertEquals(productDTO.getPrice(), updatedProduct.getPrice());
        assertEquals(productDTO.getImgURL(), updatedProduct.getImgURL());
        assertEquals(productDTO.getGroupset(), updatedProduct.getGroupset());
        assertEquals(productDTO.getMaterial(), updatedProduct.getMaterial());
        assertEquals(productDTO.getWheels(), updatedProduct.getWheels());

        verify(productRepository, times(1)).save(any(Product.class));
        verify(productVariantsRepo, times(1)).save(any(ProductVariants.class));

        ProductVariants updatedVariant = new ProductVariants();
        updatedVariant.setId(variantDTO.getId());
        updatedVariant.setStock(variantDTO.getStock());
        updatedVariant.setPrice(variantDTO.getPrice());
        updatedVariant.setSize(variantDTO.getSize());
        updatedVariant.setImgURL(variantDTO.getImgURL());
        updatedVariant.setColor(variantDTO.getColor());
        updatedVariant.setProduct(updatedProduct);

        assertEquals(variantDTO.getSize(), updatedVariant.getSize());
        assertEquals(variantDTO.getStock(), updatedVariant.getStock());
        assertEquals(variantDTO.getPrice(), updatedVariant.getPrice());
        assertEquals(variantDTO.getImgURL(), updatedVariant.getImgURL());
        assertEquals(variantDTO.getColor(), updatedVariant.getColor());
    }

    @Test
    public void testCreateVariantWhenUpdatingProduct()  {
        Long productId = 1L;
        Product existingProduct = new Product();
        existingProduct.setId(productId);

        ProductVariantsDTO newVariantDTO = new ProductVariantsDTO();
        newVariantDTO.setStock(10);
        newVariantDTO.setPrice(50.0);
        newVariantDTO.setSize(42);
        newVariantDTO.setImgURL("http://new.variant.image.url");
        newVariantDTO.setColor("Red");

        List<ProductVariantsDTO> variantDTOList = new ArrayList<>();
        variantDTOList.add(newVariantDTO);

        ProductDTO updatedProductDTO = new ProductDTO();
        updatedProductDTO.setId(productId);
        updatedProductDTO.setName("Updated Name");
        updatedProductDTO.setDescription("Updated Description");
        updatedProductDTO.setPrice(100.0);
        updatedProductDTO.setImgURL("http://updated.image.url");
        updatedProductDTO.setGroupset("groupset");
        updatedProductDTO.setMaterial("material");
        updatedProductDTO.setWheels("wheels");
        updatedProductDTO.setProdctVariantsList(variantDTOList);

        Product updatedProduct = new Product();
        updatedProduct.setId(productId);
        updatedProduct.setName("Updated Name");
        updatedProduct.setDescription("Updated Description");
        updatedProduct.setPrice(100.0);
        updatedProduct.setImgURL("http://updated.image.url");
        updatedProduct.setGroupset("groupset");
        updatedProduct.setMaterial("material");
        updatedProduct.setWheels("wheels");
        updatedProduct.setProductVariants(new HashSet<>());

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productVariantsRepo.findById(anyLong())).thenReturn(Optional.empty());
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);
        when(productVariantsRepo.save(any(ProductVariants.class))).thenReturn(new ProductVariants());

        // Act
        ResponseEntity<Product> response = adminController.updateProduct(updatedProductDTO);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedProductDTO.getName(), response.getBody().getName());

        // Capture the saved variants
        ArgumentCaptor<ProductVariants> variantCaptor = ArgumentCaptor.forClass(ProductVariants.class);
        verify(productVariantsRepo, times(1)).save(variantCaptor.capture());
        ProductVariants savedVariant = variantCaptor.getValue();

        assertEquals(newVariantDTO.getStock(), savedVariant.getStock());
        assertEquals(newVariantDTO.getPrice(), savedVariant.getPrice());
        assertEquals(newVariantDTO.getSize(), savedVariant.getSize());
        assertEquals(newVariantDTO.getImgURL(), savedVariant.getImgURL());
        assertEquals(newVariantDTO.getColor(), savedVariant.getColor());
    }




}
