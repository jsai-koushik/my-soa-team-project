package com.klef.ms.sdp.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_table")
public class Order {

    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;

    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "product_id", nullable = false)
    private long productId;

    @Column(name = "purchase_status", nullable = false)
    private String purchaseStatus;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;


    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }


    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }


    public String getPurchaseStatus() {
        return purchaseStatus;
    }

    public void setPurchaseStatus(String purchaseStatus) {
        this.purchaseStatus = purchaseStatus;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }


    @Override
    public String toString() {
        return "Order [orderId=" + orderId
                + ", userId=" + userId
                + ", productId=" + productId
                + ", purchaseStatus=" + purchaseStatus
                + ", createdAt=" + createdAt
                + "]";
    }
}