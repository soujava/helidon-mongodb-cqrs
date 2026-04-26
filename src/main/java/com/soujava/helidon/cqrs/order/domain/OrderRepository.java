package com.soujava.helidon.cqrs.order.domain;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for the {@link Order} aggregate.
 *
 * <p>The interface lives in the domain layer; the implementation lives in the
 * infrastructure layer, keeping the domain free of persistence concerns.</p>
 */
public interface OrderRepository {

    /**
     * Persists a new order or replaces an existing one with the same id.
     */
    void save(Order order);

    /**
     * Finds an order by its identity.
     */
    Optional<Order> findById(OrderId id);

    /**
     * Returns all orders.
     */
    List<Order> findAll();
}
