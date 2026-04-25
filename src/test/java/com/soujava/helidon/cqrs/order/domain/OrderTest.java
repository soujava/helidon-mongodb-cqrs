package com.soujava.helidon.cqrs.order.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderTest {

    @Test
    void shouldCreateOrderWithPendingStatus() {
        List<OrderItem> items = List.of(
                new OrderItem("product-1", 2, BigDecimal.valueOf(10.00)));

        Order order = Order.create("customer-1", items);

        assertEquals(OrderStatus.PENDING, order.getStatus());
        assertEquals("customer-1", order.getCustomerId());
        assertNotNull(order.getId());
        assertEquals(1, order.getDomainEvents().size());
    }

    @Test
    void shouldCalculateTotalAmount() {
        List<OrderItem> items = List.of(
                new OrderItem("product-1", 2, BigDecimal.valueOf(10.00)),
                new OrderItem("product-2", 1, BigDecimal.valueOf(25.00)));

        Order order = Order.create("customer-1", items);

        assertEquals(0, BigDecimal.valueOf(45.00).compareTo(order.getTotalAmount()));
    }

    @Test
    void shouldConfirmPendingOrder() {
        Order order = orderWithOneItem();

        order.confirm();

        assertEquals(OrderStatus.CONFIRMED, order.getStatus());
    }

    @Test
    void shouldCancelOrder() {
        Order order = orderWithOneItem();

        order.cancel();

        assertEquals(OrderStatus.CANCELLED, order.getStatus());
    }

    @Test
    void shouldNotConfirmAlreadyConfirmedOrder() {
        Order order = orderWithOneItem();
        order.confirm();

        assertThrows(IllegalStateException.class, order::confirm);
    }

    @Test
    void shouldNotConfirmCancelledOrder() {
        Order order = orderWithOneItem();
        order.cancel();

        assertThrows(IllegalStateException.class, order::confirm);
    }

    @Test
    void shouldNotCancelAlreadyCancelledOrder() {
        Order order = orderWithOneItem();
        order.cancel();

        assertThrows(IllegalStateException.class, order::cancel);
    }

    @Test
    void shouldNotCreateOrderWithNoItems() {
        assertThrows(IllegalArgumentException.class,
                () -> Order.create("customer-1", List.of()));
    }

    @Test
    void shouldNotCreateOrderWithNullItems() {
        assertThrows(IllegalArgumentException.class,
                () -> Order.create("customer-1", null));
    }

    @Test
    void shouldRejectOrderItemWithNegativeQuantity() {
        assertThrows(IllegalArgumentException.class,
                () -> new OrderItem("product-1", -1, BigDecimal.valueOf(10.00)));
    }

    @Test
    void shouldRejectOrderItemWithZeroQuantity() {
        assertThrows(IllegalArgumentException.class,
                () -> new OrderItem("product-1", 0, BigDecimal.valueOf(10.00)));
    }

    @Test
    void shouldRejectOrderItemWithNegativePrice() {
        assertThrows(IllegalArgumentException.class,
                () -> new OrderItem("product-1", 1, BigDecimal.valueOf(-5.00)));
    }

    // ---- helpers ----

    private Order orderWithOneItem() {
        return Order.create("customer-1",
                List.of(new OrderItem("product-1", 1, BigDecimal.valueOf(50.00))));
    }
}
