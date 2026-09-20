package com.klef.ms.sdp.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.klef.ms.sdp.dto.ProductResponse;

@FeignClient(name = "ProductService")
public interface ProductClient {
 
 @GetMapping("/product/displayall")
 List<ProductResponse> displayAllProducts();

}