package com.soujava.helidon.cqrs.shared.query;

/**
 * Handles a specific type of query and returns a read model.
 *
 * @param <Q> the query type
 * @param <R> the result type
 */
public interface QueryHandler<Q extends Query<?>, R> {

    /**
     * Returns the query type this handler is responsible for.
     */
    Class<Q> queryType();

    /**
     * Handles the given query and returns the read model.
     */
    R handle(Q query);
}
