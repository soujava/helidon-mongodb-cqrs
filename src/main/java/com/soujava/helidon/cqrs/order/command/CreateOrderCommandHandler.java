package com.soujava.helidon.cqrs.order.command;

import com.soujava.helidon.cqrs.shared.command.CommandHandler;
import com.soujava.helidon.cqrs.order.domain.Order;
import com.soujava.helidon.cqrs.order.domain.OrderItem;
import com.soujava.helidon.cqrs.order.domain.OrderRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles {@link CreateOrderCommand}: validates the request, creates the
 * {@link Order} aggregate, and persists it via the repository.
 *
 * @return the generated order id as a {@code String}
 */
@ApplicationScoped
public class CreateOrderCommandHandler implements CommandHandler<CreateOrderCommand, String> {

    private final OrderRepository orderRepository;

    @Inject
    public CreateOrderCommandHandler(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Class<CreateOrderCommand> commandType() {
        return CreateOrderCommand.class;
    }

    @Override
    public String handle(CreateOrderCommand command) {
        List<OrderItem> items = command.getItems().stream()
                .map(data -> new OrderItem(data.productId(), data.quantity(), data.unitPrice()))
                .collect(Collectors.toList());

        Order order = Order.create(command.getCustomerId(), items);
        orderRepository.save(order);

        return order.getId().getValue();
    }
}
