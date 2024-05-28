package com.luxury.dto;

import com.luxury.models.ProductVariants;

import java.util.List;

public class ProductDTO {
    private Long id;
    public String name;
    public String description;
    public Number price;
    public String imgURL;
    public String groupset;
    public String material;
    public String wheels;

    private List<ProductVariantsDTO> prodctVariantsList;

    public ProductDTO() {
    }

    public ProductDTO(Long id, String name, String description, Number price, String imgURL, String groupset, String material, String wheels, List<ProductVariantsDTO> prodctVariantsList) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgURL = imgURL;
        this.groupset = groupset;
        this.material = material;
        this.wheels = wheels;
        this.prodctVariantsList = prodctVariantsList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Number getPrice() {
        return price;
    }

    public void setPrice(Number price) {
        this.price = price;
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

    public List<ProductVariantsDTO> getProdctVariantsList() {
        return prodctVariantsList;
    }

    public void setProdctVariantsList(List<ProductVariantsDTO> prodctVariantsList) {
        this.prodctVariantsList = prodctVariantsList;
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", imgURL='" + imgURL + '\'' +
                ", groupset='" + groupset + '\'' +
                ", material='" + material + '\'' +
                ", wheels='" + wheels + '\'' +
                ", prodctVariantsList=" + prodctVariantsList +
                '}';
    }
}
