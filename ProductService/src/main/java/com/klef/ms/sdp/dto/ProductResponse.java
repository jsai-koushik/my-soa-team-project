package com.klef.ms.sdp.dto;

public class ProductResponse {

    private Long productId;

    private String name;

    private String type;

    private Double price;



    public ProductResponse() {
    }

    public ProductResponse(Long productId, String name, String type,
                           Double price, String downloadUrl) {
        this.productId = productId;
        this.name = name;
        this.type = type;
        this.price = price;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}