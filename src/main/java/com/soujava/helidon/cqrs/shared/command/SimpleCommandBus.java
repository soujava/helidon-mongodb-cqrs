package com.soujava.helidon.cqrs.shared.command;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Simple in-process command bus backed by a handler registry.
 * Handlers are registered at application startup via {@code OrderHandlerRegistrar}.
 */
@ApplicationScoped
public class SimpleCommandBus implements CommandBus {

    private final Map<Class<?>, CommandHandler<?, ?>> registry = new ConcurrentHashMap<>();

    /**
     * Registers a command handler for the command type it declares.
     */
    public void register(CommandHandler<?, ?> handler) {
        registry.put(handler.commandType(), handler);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <C extends Command, R> R dispatch(C command) {
        CommandHandler<C, R> handler = (CommandHandler<C, R>) registry.get(command.getClass());
        if (handler == null) {
            throw new IllegalStateException(
                    "No handler registered for command: " + command.getClass().getSimpleName());
        }
        return handler.handle(command);
    }
}
