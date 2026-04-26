package com.soujava.helidon.cqrs.order.domain.event;

import com.soujava.helidon.cqrs.shared.domain.DomainEvent;
import com.soujava.helidon.cqrs.order.domain.OrderId;

/**
 * Domain event raised when an order is cancelled.
 */
public class OrderCancelledEvent extends DomainEvent {

    private final OrderId orderId;

    public OrderCancelledEvent(OrderId orderId) {
        super();
        this.orderId = orderId;
    }

    public OrderId getOrderId() {
        return orderId;
    }
}
