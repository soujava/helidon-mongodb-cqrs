package com.acme.cards.command;

import java.math.BigDecimal;
import java.util.UUID;

public record AuthorizeCardCommand(
        UUID cardId,
        BigDecimal amount
) {
}