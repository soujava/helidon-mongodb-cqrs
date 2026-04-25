package com.soujava.helidon.cqrs.order.command;

import com.soujava.helidon.cqrs.shared.command.Command;

/**
 * Command to confirm a pending order.
 */
public class ConfirmOrderCommand implements Command {

    private final String orderId;

    public ConfirmOrderCommand(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }
}
