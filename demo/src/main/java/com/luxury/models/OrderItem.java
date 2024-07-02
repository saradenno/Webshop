package com.luxury.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private PlacedOrder order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "variant_id", nullable = true)
    private ProductVariants variant;

    private int quantity;

    public OrderItem() {
    }

    public OrderItem(PlacedOrder order, Product product, ProductVariants variant, int quantity) {
        this.order = order;
        this.product = product;
        this.variant = variant;
        this.quantity = quantity;
    }

    // Getters and setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PlacedOrder getOrder() {
        return order;
    }

    public void setOrder(PlacedOrder order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductVariants getVariant() {
        return variant;
    }

    public void setVariant(ProductVariants variant) {
        this.variant = variant;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
