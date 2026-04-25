package com.soujava.helidon.cqrs.order.query;

import com.soujava.helidon.cqrs.shared.query.QueryHandler;
import com.soujava.helidon.cqrs.order.domain.Order;
import com.soujava.helidon.cqrs.order.domain.OrderId;
import com.soujava.helidon.cqrs.order.domain.OrderRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles {@link FindOrderByIdQuery} and maps the aggregate to an {@link OrderView}.
 */
@ApplicationScoped
public class FindOrderByIdQueryHandler implements QueryHandler<FindOrderByIdQuery, OrderView> {

    private final OrderRepository orderRepository;

    @Inject
    public FindOrderByIdQueryHandler(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Class<FindOrderByIdQuery> queryType() {
        return FindOrderByIdQuery.class;
    }

    @Override
    public OrderView handle(FindOrderByIdQuery query) {
        return orderRepository.findById(OrderId.of(query.getOrderId()))
                .map(this::toView)
                .orElse(null);
    }

    private OrderView toView(Order order) {
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
