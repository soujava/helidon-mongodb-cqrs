package com.soujava.helidon.cqrs.order.query;

import java.math.BigDecimal;
import java.util.List;

/**
 * Read model (view) for an order, returned by query handlers.
 * This is intentionally separate from the domain {@code Order} aggregate
 * to allow the query side to evolve independently of the write side.
 */
public class OrderView {

    private final String id;
    private final String customerId;
    private final String status;
    private final BigDecimal totalAmount;
    private final List<OrderItemView> items;

    public OrderView(String id, String customerId, String status,
                     BigDecimal totalAmount, List<OrderItemView> items) {
        this.id = id;
        this.customerId = customerId;
        this.status = status;
        this.totalAmount = totalAmount;
        this.items = items;
    }

    public String getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getStatus() {
        return status;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public List<OrderItemView> getItems() {
        return items;
    }

    /**
     * Read model for a single order line item.
     */
    public record OrderItemView(String productId, int quantity, BigDecimal unitPrice, BigDecimal subtotal) {
    }
}
