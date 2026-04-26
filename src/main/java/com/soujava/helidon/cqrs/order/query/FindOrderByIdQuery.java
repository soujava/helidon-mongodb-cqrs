package com.soujava.helidon.cqrs.order.query;

import com.soujava.helidon.cqrs.shared.query.Query;

/**
 * Query to retrieve a single order by its id.
 */
public class FindOrderByIdQuery implements Query<OrderView> {

    private final String orderId;

    public FindOrderByIdQuery(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }
}
