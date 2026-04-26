package com.soujava.helidon.cqrs.order.command;

import com.soujava.helidon.cqrs.shared.command.Command;

import java.math.BigDecimal;
import java.util.List;

/**
 * Command to place a new order for a customer.
 */
public class CreateOrderCommand implements Command {

    private final String customerId;
    private final List<OrderItemData> items;

    public CreateOrderCommand(String customerId, List<OrderItemData> items) {
        this.customerId = customerId;
        this.items = items;
    }

    public String getCustomerId() {
        return customerId;
    }

    public List<OrderItemData> getItems() {
        return items;
    }

    /**
     * Immutable data transfer object for a single order line item.
     */
    public record OrderItemData(String productId, int quantity, BigDecimal unitPrice) {
    }
}
