package com.acme.payment;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentRequest(UUID productCode,
                             String productName,
                             int quantity,
                             BigDecimal unitPrice) {
}
