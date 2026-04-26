package com.soujava.helidon.cqrs.order.domain;

import java.util.Objects;
import java.util.UUID;

/**
 * Value object representing the unique identity of an {@link Order}.
 */
public final class OrderId {

    private final String value;

    private OrderId(String value) {
        this.value = Objects.requireNonNull(value, "OrderId value must not be null");
    }

    public static OrderId generate() {
        return new OrderId(UUID.randomUUID().toString());
    }

    public static OrderId of(String value) {
        return new OrderId(value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderId orderId = (OrderId) o;
        return Objects.equals(value, orderId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
