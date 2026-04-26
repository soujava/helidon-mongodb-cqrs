package com.soujava.helidon.cqrs.order.query;

import com.soujava.helidon.cqrs.shared.query.QueryHandler;
import com.soujava.helidon.cqrs.order.domain.Order;
import com.soujava.helidon.cqrs.order.domain.OrderId;
import com.soujava.helidon.cqrs.order.domain.OrderRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

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
                .map(OrderViewMapper::toView)
                .orElse(null);
    }
}
