package com.klef.ms.sdp.controller;

import java.util.List;
import com.klef.ms.sdp.dto.ProductDownloadResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.klef.ms.sdp.dto.ProductRequest;
import com.klef.ms.sdp.dto.ProductResponse;
import com.klef.ms.sdp.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public ProductResponse addProduct(@RequestBody ProductRequest request) {

        return productService.addProduct(request);
    }

    @GetMapping("/displayall")
    public List<ProductResponse> displayAllProducts() {

        return productService.displayAllProducts();
    }

    @GetMapping("/displaybyid/{id}")
    public ProductResponse displayProductById(@PathVariable Long id) {

        return productService.displayProductById(id);
    }

    @PutMapping("/update/{id}")
    public ProductResponse updateProduct(
            @PathVariable Long id,
            @RequestBody ProductRequest request) {

        return productService.updateProduct(id, request);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {

        return productService.deleteProduct(id);
    }
    @GetMapping("/download/{productId}")
    public ProductDownloadResponse getProductForDownload(
            @PathVariable Long productId) {

        return productService.getProductForDownload(productId);
    }
}