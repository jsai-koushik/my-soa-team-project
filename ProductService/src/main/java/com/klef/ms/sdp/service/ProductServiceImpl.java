package com.klef.ms.sdp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.ms.sdp.entity.Product;
import com.klef.ms.sdp.dto.ProductDownloadResponse;
import com.klef.ms.sdp.dto.ProductRequest;
import com.klef.ms.sdp.dto.ProductResponse;
import com.klef.ms.sdp.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repository;

    @Override
    public ProductResponse addProduct(ProductRequest request) {

        Product product = new Product();

        product.setName(request.getName());
        product.setType(request.getType());
        product.setPrice(request.getPrice());
        product.setDownloadUrl(request.getDownloadUrl());

        Product savedProduct = repository.save(product);

        return mapToResponse(savedProduct);
    }

    @Override
    public List<ProductResponse> displayAllProducts() {

        List<Product> products = repository.findAll();

        List<ProductResponse> responses = new ArrayList<>();

        for (Product product : products) {

            ProductResponse response = mapToResponse(product);

            responses.add(response);
        }

        return responses;
    }

    @Override
    public ProductResponse displayProductById(Long id) {

        Product product = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Product not found with id : " + id));

        return mapToResponse(product);
    }

    @Override
    public ProductResponse updateProduct(
            Long id, ProductRequest request) {

        Product product = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Product not found with id : " + id));

        product.setName(request.getName());
        product.setType(request.getType());
        product.setPrice(request.getPrice());
        product.setDownloadUrl(request.getDownloadUrl());

        Product updatedProduct = repository.save(product);

        return mapToResponse(updatedProduct);
    }

    @Override
    public String deleteProduct(Long id) {

        Product product = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Product not found with id : " + id));

        repository.delete(product);

        return "Product deleted successfully";
    }

    private ProductResponse mapToResponse(Product product) {

        ProductResponse response = new ProductResponse();

        response.setProductId(product.getProductId());
        response.setName(product.getName());
        response.setType(product.getType());
        response.setPrice(product.getPrice());

        return response;
    }
    @Override
    public ProductDownloadResponse getProductForDownload(Long productId) {

        Product product = repository.findById(productId)
                .orElseThrow(() ->
                        new RuntimeException("Product not found with id : " + productId));

        ProductDownloadResponse response = new ProductDownloadResponse();

        response.setProductId(product.getProductId());
        response.setName(product.getName());
        response.setDownloadUrl(product.getDownloadUrl());

        return response;
    }
}