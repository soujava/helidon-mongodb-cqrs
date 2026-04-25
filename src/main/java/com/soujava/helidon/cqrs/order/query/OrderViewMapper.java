package com.soujava.helidon.cqrs.order.query;

import com.soujava.helidon.cqrs.order.domain.Order;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Converts {@link Order} aggregates to {@link OrderView} read models.
 * Centralises mapping logic so command and query handlers share a single implementation.
 */
final class OrderViewMapper {

    private OrderViewMapper() {
    }

    static OrderView toView(Order order) {
        List<OrderView.OrderItemView> itemViews = order.getItems().stream()
                .map(item -> new OrderView.OrderItemView(
                        item.getProductId(),
                        item.getQuantity(),
                        item.getUnitPrice(),
                        item.getSubtotal()))
                .collect(Collectors.toList());

        return new OrderView(
                order.getId().getValue(),
                order.getCustomerId(),
                order.getStatus().name(),
                order.getTotalAmount(),
                itemViews);
    }
}
