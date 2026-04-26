package com.soujava.helidon.cqrs.order.command;

import com.soujava.helidon.cqrs.shared.command.CommandHandler;
import com.soujava.helidon.cqrs.order.domain.Order;
import com.soujava.helidon.cqrs.order.domain.OrderId;
import com.soujava.helidon.cqrs.order.domain.OrderRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * Handles {@link CancelOrderCommand}: loads the order, cancels it, and saves.
 */
@ApplicationScoped
public class CancelOrderCommandHandler implements CommandHandler<CancelOrderCommand, Void> {

    private final OrderRepository orderRepository;

    @Inject
    public CancelOrderCommandHandler(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Class<CancelOrderCommand> commandType() {
        return CancelOrderCommand.class;
    }

    @Override
    public Void handle(CancelOrderCommand command) {
        Order order = orderRepository.findById(OrderId.of(command.getOrderId()))
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + command.getOrderId()));
        order.cancel();
        orderRepository.save(order);
        return null;
    }
}
