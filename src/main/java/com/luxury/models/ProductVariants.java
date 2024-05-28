package com.luxury.models;

import jakarta.persistence.*;

@Entity
public class ProductVariants {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int size;
    private String color;
    @ManyToOne
    @JoinColumn(name ="product_id")
    private Product product;
    private int stock;
    private double price;

    public ProductVariants() {
    }

    public ProductVariants(Long id, int size, String color, Product product, int stock, double price) {
        this.id = id;
        this.size = size;
        this.color = color;
        this.product = product;
        this.stock = stock;
        this.price = price;
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
}
