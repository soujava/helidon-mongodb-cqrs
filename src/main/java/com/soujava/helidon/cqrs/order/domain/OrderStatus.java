package com.soujava.helidon.cqrs.order.domain;

/**
 * Value object representing the lifecycle status of an {@link Order}.
 */
public enum OrderStatus {
    PENDING,
    CONFIRMED,
    CANCELLED
}
