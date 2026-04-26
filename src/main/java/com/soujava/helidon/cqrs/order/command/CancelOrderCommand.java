package com.soujava.helidon.cqrs.order.command;

import com.soujava.helidon.cqrs.shared.command.Command;

/**
 * Command to cancel an existing order.
 */
public class CancelOrderCommand implements Command {

    private final String orderId;

    public CancelOrderCommand(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }
}
