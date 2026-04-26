package com.soujava.helidon.cqrs.shared.command;

/**
 * Dispatches commands to their respective handlers.
 */
public interface CommandBus {

    /**
     * Dispatches the given command to its registered handler.
     *
     * @param command the command to dispatch
     * @param <C>     the command type
     * @param <R>     the result type
     * @return the result produced by the handler
     */
    <C extends Command, R> R dispatch(C command);
}
