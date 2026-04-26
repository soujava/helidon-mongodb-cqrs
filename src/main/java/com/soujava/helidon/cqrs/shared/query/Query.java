package com.soujava.helidon.cqrs.shared.query;

/**
 * Marker interface for all queries in the CQRS pattern.
 * Queries represent the intent to read state without modifying it.
 *
 * @param <R> the type of the result this query produces
 */
public interface Query<R> {
}
