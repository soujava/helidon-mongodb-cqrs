package com.soujava.helidon.cqrs.shared.command;

/**
 * Handles a specific type of command and returns a result.
 *
 * @param <C> the command type
 * @param <R> the result type
 */
public interface CommandHandler<C extends Command, R> {

    /**
     * Returns the command type this handler is responsible for.
     */
    Class<C> commandType();

    /**
     * Handles the given command and returns the result.
     */
    R handle(C command);
}
