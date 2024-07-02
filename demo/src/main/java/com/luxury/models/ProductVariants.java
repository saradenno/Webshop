package com.luxury.models;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
public class ProductVariants {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int size;
    private String color;
    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference // Applied to the child side of the relationship
    private Product product;
    private int stock;
    private double price;

    private String imgURL;
    

//    @ManyToMany(mappedBy = "productVariants")
//    @JsonIgnore // To prevent infinite recursion
//    private Set<PlacedOrder> orders = new HashSet<>();


    public ProductVariants() {
    }

    public ProductVariants(Long id, int size, String color, Product product, int stock, double price, String imgURL) {
        this.id = id;
        this.size = size;
        this.color = color;
        this.product = product;
        this.stock = stock;
        this.price = price;
        this.imgURL=imgURL;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }
    
//    public Set<PlacedOrder> getOrders() {
//		return orders;
//	}
//    
//    public void setOrders(Set<PlacedOrder> orders) {
//		this.orders = orders;
//	}

	@Override
	public String toString() {
		return "ProductVariants [id=" + id + ", size=" + size + ", color=" + color + ", product=" + product + ", stock="
				+ stock + ", price=" + price + ", imgURL=" + imgURL + "]";
	}
    
}
