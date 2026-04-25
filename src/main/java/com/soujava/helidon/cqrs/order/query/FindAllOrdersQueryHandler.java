package com.soujava.helidon.cqrs.order.query;

import com.soujava.helidon.cqrs.shared.query.QueryHandler;
import com.soujava.helidon.cqrs.order.domain.OrderRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles {@link FindAllOrdersQuery} and maps each aggregate to an {@link OrderView}.
 */
@ApplicationScoped
public class FindAllOrdersQueryHandler implements QueryHandler<FindAllOrdersQuery, List<OrderView>> {

    private final OrderRepository orderRepository;

    @Inject
    public FindAllOrdersQueryHandler(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Class<FindAllOrdersQuery> queryType() {
        return FindAllOrdersQuery.class;
    }

    @Override
    public List<OrderView> handle(FindAllOrdersQuery query) {
        return orderRepository.findAll().stream()
                .map(OrderViewMapper::toView)
                .collect(Collectors.toList());
    }
}
