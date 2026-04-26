package com.soujava.helidon.cqrs.order.api;

import java.math.BigDecimal;
import java.util.List;

/**
 * Request body for creating a new order ({@code POST /orders}).
 */
public class CreateOrderRequest {

    private String customerId;
    private List<OrderItemRequest> items;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<OrderItemRequest> getItems() {
        return items;
    }

    public void setItems(List<OrderItemRequest> items) {
        this.items = items;
    }

    /**
     * A single line item within the create-order request.
     */
    public static class OrderItemRequest {

        private String productId;
        private int quantity;
        private BigDecimal unitPrice;

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public BigDecimal getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(BigDecimal unitPrice) {
            this.unitPrice = unitPrice;
        }
    }
}
