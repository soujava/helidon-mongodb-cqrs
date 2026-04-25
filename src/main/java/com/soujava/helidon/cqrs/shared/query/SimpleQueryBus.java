package com.soujava.helidon.cqrs.shared.query;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Simple in-process query bus backed by a handler registry.
 * Handlers are registered at application startup via {@code OrderHandlerRegistrar}.
 */
@ApplicationScoped
public class SimpleQueryBus implements QueryBus {

    private final Map<Class<?>, QueryHandler<?, ?>> registry = new ConcurrentHashMap<>();

    /**
     * Registers a query handler for the query type it declares.
     */
    public void register(QueryHandler<?, ?> handler) {
        registry.put(handler.queryType(), handler);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <Q extends Query<R>, R> R dispatch(Q query) {
        QueryHandler<Q, R> handler = (QueryHandler<Q, R>) registry.get(query.getClass());
        if (handler == null) {
            throw new IllegalStateException(
                    "No handler registered for query: " + query.getClass().getSimpleName());
        }
        return handler.handle(query);
    }
}
