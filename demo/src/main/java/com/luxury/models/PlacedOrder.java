package com.luxury.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class PlacedOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = true)
    private String name;

    @Column(nullable = true)
    private String infix;

    @Column(nullable = true)
    private String lastName;

    @Column(nullable = true)
    private String zipcode;

    @Column(nullable = true)
    private int houseNumber;

    @Column(nullable = true)
    private String notes;

    @Column(nullable = true)
    private int totalProducts;

    @Column(nullable = true)
    private LocalDateTime orderDate;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JsonBackReference
    private CustomUser user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<OrderItem> orderItems = new HashSet<>();

    public PlacedOrder() {
    }

    public PlacedOrder(String name, String infix, String lastName, String zipcode, int houseNumber, String notes, CustomUser user, Set<OrderItem> orderItems) {
        this.name = name;
        this.infix = infix;
        this.lastName = lastName;
        this.zipcode = zipcode;
        this.houseNumber = houseNumber;
        this.notes = notes;
        this.user = user;
        this.orderItems = orderItems;
    }

    // Getters and setters

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

    public String getInfix() {
        return infix;
    }

    public void setInfix(String infix) {
        this.infix = infix;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(int totalProducts) {
        this.totalProducts = totalProducts;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public CustomUser getUser() {
        return user;
    }

    public void setUser(CustomUser user) {
        this.user = user;
    }

    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
