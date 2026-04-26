package com.soujava.helidon.cqrs.order.domain;

import com.soujava.helidon.cqrs.shared.domain.AggregateRoot;
import com.soujava.helidon.cqrs.order.domain.event.OrderCancelledEvent;
import com.soujava.helidon.cqrs.order.domain.event.OrderConfirmedEvent;
import com.soujava.helidon.cqrs.order.domain.event.OrderCreatedEvent;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Aggregate root for the Order bounded context.
 *
 * <p>All state changes are performed through explicit business methods that
 * enforce invariants and raise domain events.</p>
 */
public class Order extends AggregateRoot {

    private final OrderId id;
    private final String customerId;
    private final List<OrderItem> items;
    private OrderStatus status;

    private Order(OrderId id, String customerId, List<OrderItem> items) {
        this.id = Objects.requireNonNull(id, "Order id must not be null");
        this.customerId = Objects.requireNonNull(customerId, "Customer id must not be null");
        this.items = new ArrayList<>(items);
        this.status = OrderStatus.PENDING;
    }

    /**
     * Factory method — creates a new Order and raises {@link OrderCreatedEvent}.
     */
    public static Order create(String customerId, List<OrderItem> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Order must have at least one item");
        }
        OrderId id = OrderId.generate();
        Order order = new Order(id, customerId, items);
        order.registerEvent(new OrderCreatedEvent(id, customerId));
        return order;
    }

    /**
     * Reconstitution constructor used by the repository to rebuild an Order from persistence.
     */
    public static Order reconstitute(OrderId id, String customerId, List<OrderItem> items, OrderStatus status) {
        Order order = new Order(id, customerId, items);
        order.status = status;
        return order;
    }

    /**
     * Confirms the order. Only a {@link OrderStatus#PENDING} order can be confirmed.
     */
    public void confirm() {
        if (status != OrderStatus.PENDING) {
            throw new IllegalStateException("Only PENDING orders can be confirmed. Current status: " + status);
        }
        this.status = OrderStatus.CONFIRMED;
        registerEvent(new OrderConfirmedEvent(id));
    }

    /**
     * Cancels the order. An already cancelled order cannot be cancelled again.
     */
    public void cancel() {
        if (status == OrderStatus.CANCELLED) {
            throw new IllegalStateException("Order is already cancelled");
        }
        this.status = OrderStatus.CANCELLED;
        registerEvent(new OrderCancelledEvent(id));
    }

    public BigDecimal getTotalAmount() {
        return items.stream()
                .map(OrderItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public OrderId getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public OrderStatus getStatus() {
        return status;
    }
}
