package com.klef.ms.sdp.dto;

public class ProductRequest {

    private String name;

    private String type;

    private Double price;

    private String downloadUrl;

    public ProductRequest() {
    }

    public ProductRequest(String name, String type, Double price, String downloadUrl) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.downloadUrl = downloadUrl;
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

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}