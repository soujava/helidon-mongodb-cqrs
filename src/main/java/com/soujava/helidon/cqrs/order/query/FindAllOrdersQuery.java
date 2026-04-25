package com.soujava.helidon.cqrs.order.query;

import com.soujava.helidon.cqrs.shared.query.Query;

import java.util.List;

/**
 * Query to retrieve all orders.
 */
public class FindAllOrdersQuery implements Query<List<OrderView>> {
}
