package com.klef.ms.sdp.service;

import java.util.List;

import com.klef.ms.sdp.dto.ProductDownloadResponse;
import com.klef.ms.sdp.dto.ProductRequest;
import com.klef.ms.sdp.dto.ProductResponse;

public interface ProductService {

    ProductResponse addProduct(ProductRequest request);

    List<ProductResponse> displayAllProducts();
    ProductResponse displayProductById(Long id);
    ProductResponse updateProduct(Long id, ProductRequest request);
    String deleteProduct(Long id);
    ProductDownloadResponse getProductForDownload(Long productId);
}