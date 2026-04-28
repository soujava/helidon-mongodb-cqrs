package com.acme.cards;

import jakarta.nosql.Column;
import jakarta.nosql.Entity;
import jakarta.nosql.Id;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
public class CardOperation {

    @Id
    private UUID operationId;

    @Column
    private UUID cardId;

    @Column
    private BigDecimal amount;

    @Column
    private Type type;

    @Column
    private Instant requestedAt;

    public enum Type {
        AUTHORIZE
    }

    CardOperation() {
    }

    public CardOperation(UUID operationId, UUID cardId, BigDecimal amount, Type type, Instant requestedAt) {
        this.operationId = operationId;
        this.cardId = cardId;
        this.amount = amount;
        this.type = type;
        this.requestedAt = requestedAt;
    }

    public UUID getOperationId() { return operationId; }
    public UUID getCardId() { return cardId; }
    public BigDecimal getAmount() { return amount; }
    public Type getType() { return type; }
}