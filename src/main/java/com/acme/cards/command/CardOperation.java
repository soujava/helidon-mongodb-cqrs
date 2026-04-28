package com.acme.cards.command;

import jakarta.nosql.Column;
import jakarta.nosql.Entity;
import jakarta.nosql.Id;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
public record CardOperation(
        @Id UUID operationId,
        @Column UUID cardId,
        @Column BigDecimal amount,
        @Column CardOperationType cardOperationType,
        @Column Instant requestedAt
) {
}