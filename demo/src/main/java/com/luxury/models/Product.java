package com.luxury.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Product {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private Number price;
    private String description;
    private String imgURL;
    private String groupset;
    private String material;
    private String wheels;
    @Transient
    private int amount;
    
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonManagedReference // Applied to the parent side of the relationship
    private Set<ProductVariants> productVariants = new HashSet<>();

//    @ManyToMany(mappedBy = "products")
//    @JsonIgnore // To prevent infinite recursion
//    private Set<PlacedOrder> orders = new HashSet<>();


    //needed by JPA to create the entity must be present no arg constructor
    public Product() {
    }

    public Product(String name, Number price, String description, String imgURL, String groupset, String material, String wheels ) {

        this.name = name;
        this.price = price;
        this.description = description;
        this.imgURL = imgURL;
        this.groupset = groupset;
        this.material = material;
        this.wheels = wheels;
    }

    //getters and setters are needed to map all the properties to the database by JPA, could
    //also be solved by making the properties public but gives less control over the properties.

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Number getPrice() { return price; }

    public void setPrice(Number price) { this.price = price; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getGroupset() {
        return groupset;
    }

    public void setGroupset(String groupset) {
        this.groupset = groupset;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getWheels() {
        return wheels;
    }

    public void setWheels(String wheels) {
        this.wheels = wheels;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
    
    public Set<ProductVariants> getProductVariants() {
		return productVariants;
	}
    
    public void setProductVariants(Set<ProductVariants> productVariants) {
		this.productVariants = productVariants;
	}
    
//    public Set<PlacedOrder> getOrders() {
//		return orders;
//	}
//    
//    public void setOrders(Set<PlacedOrder> orders) {
//		this.orders = orders;
//	}
}
