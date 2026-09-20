package com.klef.ms.sdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.klef.ms.sdp.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}