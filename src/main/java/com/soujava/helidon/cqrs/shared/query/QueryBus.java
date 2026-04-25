package com.soujava.helidon.cqrs.shared.query;

/**
 * Dispatches queries to their respective handlers.
 */
public interface QueryBus {

    /**
     * Dispatches the given query to its registered handler.
     *
     * @param query the query to dispatch
     * @param <Q>   the query type
     * @param <R>   the result type
     * @return the read model produced by the handler
     */
    <Q extends Query<R>, R> R dispatch(Q query);
}
