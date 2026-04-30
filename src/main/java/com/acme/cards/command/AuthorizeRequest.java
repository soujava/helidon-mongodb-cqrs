package com.acme.cards.command;

import java.math.BigDecimal;

public record AuthorizeRequest(BigDecimal amount, String reason) {

}
