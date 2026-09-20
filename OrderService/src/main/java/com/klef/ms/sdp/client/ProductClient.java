package com.klef.ms.sdp.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.klef.ms.sdp.dto.ProductDownloadResponse;

@FeignClient(name = "ProductService")
public interface ProductClient {

    @GetMapping("/product/download/{productId}")
    ProductDownloadResponse getProductForDownload(
            @PathVariable("productId") Long productId);
}