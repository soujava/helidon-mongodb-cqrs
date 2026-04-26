package com.soujava.helidon.cqrs.order.command;

import com.soujava.helidon.cqrs.shared.command.CommandHandler;
import com.soujava.helidon.cqrs.order.domain.Order;
import com.soujava.helidon.cqrs.order.domain.OrderId;
import com.soujava.helidon.cqrs.order.domain.OrderRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * Handles {@link ConfirmOrderCommand}: loads the order, confirms it, and saves.
 */
@ApplicationScoped
public class ConfirmOrderCommandHandler implements CommandHandler<ConfirmOrderCommand, Void> {

    private final OrderRepository orderRepository;

    @Inject
    public ConfirmOrderCommandHandler(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Class<ConfirmOrderCommand> commandType() {
        return ConfirmOrderCommand.class;
    }

    @Override
    public Void handle(ConfirmOrderCommand command) {
        Order order = orderRepository.findById(OrderId.of(command.getOrderId()))
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + command.getOrderId()));
        order.confirm();
        orderRepository.save(order);
        return null;
    }
}
