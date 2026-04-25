package com.soujava.helidon.cqrs.order;

import com.soujava.helidon.cqrs.order.command.CancelOrderCommandHandler;
import com.soujava.helidon.cqrs.order.command.ConfirmOrderCommandHandler;
import com.soujava.helidon.cqrs.order.command.CreateOrderCommandHandler;
import com.soujava.helidon.cqrs.order.query.FindAllOrdersQueryHandler;
import com.soujava.helidon.cqrs.order.query.FindOrderByIdQueryHandler;
import com.soujava.helidon.cqrs.shared.command.SimpleCommandBus;
import com.soujava.helidon.cqrs.shared.query.SimpleQueryBus;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

/**
 * Registers all command and query handlers for the Order feature with the
 * respective buses at application startup.
 *
 * <p>This keeps the bus decoupled from any specific feature while still
 * allowing features to wire themselves in a single, explicit location.</p>
 */
@ApplicationScoped
public class OrderHandlerRegistrar {

    @Inject
    private SimpleCommandBus commandBus;

    @Inject
    private SimpleQueryBus queryBus;

    @Inject
    private CreateOrderCommandHandler createOrderCommandHandler;

    @Inject
    private ConfirmOrderCommandHandler confirmOrderCommandHandler;

    @Inject
    private CancelOrderCommandHandler cancelOrderCommandHandler;

    @Inject
    private FindOrderByIdQueryHandler findOrderByIdQueryHandler;

    @Inject
    private FindAllOrdersQueryHandler findAllOrdersQueryHandler;

    void onApplicationStart(@Observes @Initialized(ApplicationScoped.class) Object event) {
        commandBus.register(createOrderCommandHandler);
        commandBus.register(confirmOrderCommandHandler);
        commandBus.register(cancelOrderCommandHandler);

        queryBus.register(findOrderByIdQueryHandler);
        queryBus.register(findAllOrdersQueryHandler);
    }
}
