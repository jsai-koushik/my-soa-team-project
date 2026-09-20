package com.klef.ms.sdp.service;

import java.util.List;

import com.klef.ms.sdp.dto.ProductDownloadResponse;
import com.klef.ms.sdp.entity.Order;

public interface OrderService {

    Order saveOrder(Order order);
    List<Order> getAllOrders();
    Order getOrderById(Long id);
    Order updateOrder(Long id, Order order);
    String deleteOrder(Long id);
    List<Order> getOrdersByUserId(Long userId);
    ProductDownloadResponse getDownloadDetails(Long orderId);
}