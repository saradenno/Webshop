package com.luxury.dto;

/**
 * @author Muhammad Umar
 */
public class ProductVariantsDTO {
    private Long id;
    private int size;
    private String color;
    private Long productId;
    private int stock;
    private double price;

    private String imgURL;

    public ProductVariantsDTO() {
    }

    public ProductVariantsDTO(Long id, int size, String color, Long productId, int stock, double price, String imgURL) {
        this.id = id;
        this.size = size;
        this.color = color;
        this.productId = productId;
        this.stock = stock;
        this.price = price;
        this.imgURL = imgURL;

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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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

	@Override
	public String toString() {
		return "ProductVariantsDTO [id=" + id + ", size=" + size + ", color=" + color + ", productId=" + productId
				+ ", stock=" + stock + ", price=" + price + ", imgURL=" + imgURL + "]";
	}
    
    
}
