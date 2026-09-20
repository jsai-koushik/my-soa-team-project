package com.klef.ms.sdp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.ms.sdp.client.ProductClient;
import com.klef.ms.sdp.dto.ProductDownloadResponse;
import com.klef.ms.sdp.entity.Order;
import com.klef.ms.sdp.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository repo;

    @Autowired
    private ProductClient productClient;

    @Override
    public Order saveOrder(Order order) {
        return repo.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return repo.findAll();
    }

    @Override
    public Order getOrderById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Order updateOrder(Long id, Order order) {

        Optional<Order> optional = repo.findById(id);

        if (optional.isPresent()) {

            Order existingOrder = optional.get();

            existingOrder.setUserId(order.getUserId());
            existingOrder.setProductId(order.getProductId());
            existingOrder.setPurchaseStatus(order.getPurchaseStatus());

            return repo.save(existingOrder);
        } 
        else {
            return null;
        }
    }

    @Override
    public String deleteOrder(Long id) {

        boolean status = repo.existsById(id);

        if (status) {

            repo.deleteById(id);

            return "Order Deleted Successfully";
        } 
        else {

            return "Order ID Not Found";
        }
    }

    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        return repo.findByUserId(userId);
    }

    @Override
    public ProductDownloadResponse getDownloadDetails(Long orderId) {

        Optional<Order> optional = repo.findById(orderId);

        if (optional.isPresent()) {

            Order order = optional.get();

            if (order.getPurchaseStatus().equalsIgnoreCase("SUCCESS")) {

                ProductDownloadResponse response =
                        productClient.getProductForDownload(order.getProductId());

                return response;
            } 
            else {

                return null;
            }
        } 
        else {

            return null;
        }
    }
}