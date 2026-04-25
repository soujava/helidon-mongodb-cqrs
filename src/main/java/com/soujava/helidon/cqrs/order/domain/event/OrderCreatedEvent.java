package com.soujava.helidon.cqrs.order.domain.event;

import com.soujava.helidon.cqrs.shared.domain.DomainEvent;
import com.soujava.helidon.cqrs.order.domain.OrderId;

/**
 * Domain event raised when a new order is created.
 */
public class OrderCreatedEvent extends DomainEvent {

    private final OrderId orderId;
    private final String customerId;

    public OrderCreatedEvent(OrderId orderId, String customerId) {
        super();
        this.orderId = orderId;
        this.customerId = customerId;
    }

    public OrderId getOrderId() {
        return orderId;
    }

    public String getCustomerId() {
        return customerId;
    }
}
