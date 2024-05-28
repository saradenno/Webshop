package com.luxury.dao;


import com.luxury.dto.ProductDTO;
import com.luxury.dto.ProductVariantsDTO;
import com.luxury.models.Product;
import com.luxury.models.ProductVariants;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ProductDAO {

    private final ProductRepository productRepository;
    @Autowired
    public ProductVariantsRepo productVariantsRepo;

    public ProductDAO(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDTO> getAllProducts(){

        List<Product> products = this.productRepository.findAll();

        List<ProductDTO> list = new ArrayList<>();

        for(Product product: products) {
            ProductDTO productDTO = new ProductDTO();
            List<ProductVariantsDTO> listOfVariants= new ArrayList<>();


            productDTO.setId(product.getId());
            productDTO.setName(product.getName());
            productDTO.setDescription(product.getDescription());
            productDTO.setPrice(product.getPrice());
            productDTO.setImgURL(product.getImgURL());
            productDTO.setGroupset(product.getGroupset());
            productDTO.setMaterial(product.getMaterial());
            productDTO.setWheels(product.getWheels());

            for(ProductVariants v :productVariantsRepo.getAllVariantsByProductId(product.getId())){
                ProductVariantsDTO productVariantsDTO = new ProductVariantsDTO();//variants entity
                productVariantsDTO.setId(v.getId());
                productVariantsDTO.setStock(v.getStock());
                productVariantsDTO.setColor(v.getColor());
                productVariantsDTO.setSize(v.getSize());
                productVariantsDTO.setPrice(v.getPrice());
                listOfVariants.add(productVariantsDTO);
            }
            productDTO.setProdctVariantsList(listOfVariants);

            list.add(productDTO);
        }

        return list;
    }

    public ProductDTO getProductById(long id){
        Optional<Product> p = this.productRepository.findById(id);

        Product product = p.orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "No product found with that id"
        ));

        ProductDTO productDTO = new ProductDTO();//final entity to display
        List<ProductVariantsDTO> listOfVariants= new ArrayList<>();

        for(ProductVariants v :productVariantsRepo.getAllVariantsByProductId(product.getId())){
            ProductVariantsDTO productVariantsDTO = new ProductVariantsDTO();//variants entity
            productVariantsDTO.setStock(v.getStock());
            productVariantsDTO.setId(v.getId());
            productVariantsDTO.setColor(v.getColor());
            productVariantsDTO.setSize(v.getSize());
            productVariantsDTO.setPrice(v.getPrice());
            listOfVariants.add(productVariantsDTO);
        }

        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setProdctVariantsList(listOfVariants);
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setImgURL(product.getImgURL());
        productDTO.setGroupset(product.getGroupset());
        productDTO.setMaterial(product.getMaterial());
        productDTO.setWheels(product.getWheels());

        return productDTO;
    }

    @Transactional
    public void createProduct(Product product){
        this.productRepository.save(product);
    }

    @Transactional
    public void createProduct(ProductDTO productDTO){
            Product product = new Product(productDTO.name, productDTO.price,  productDTO.description,productDTO.imgURL, productDTO.groupset, productDTO.material, productDTO.wheels);

            for(ProductVariantsDTO v: productDTO.getProdctVariantsList()){
                ProductVariants newvariant =new  ProductVariants();
                newvariant.setStock(v.getStock());
                newvariant.setColor(v.getColor());
                newvariant.setSize(v.getSize());
                newvariant.setProduct(product);
                newvariant.setPrice(v.getPrice());
                productVariantsRepo.save(newvariant);
            }

            productRepository.save(product);

        }

    }
