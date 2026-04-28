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
    private CardOperationType cardOperationType;

    @Column
    private Instant requestedAt;

    CardOperation() {
    }

    public CardOperation(UUID operationId, UUID cardId, BigDecimal amount, CardOperationType cardOperationType, Instant requestedAt) {
        this.operationId = operationId;
        this.cardId = cardId;
        this.amount = amount;
        this.cardOperationType = cardOperationType;
        this.requestedAt = requestedAt;
    }

    public UUID getOperationId() { return operationId; }
    public UUID getCardId() { return cardId; }
    public BigDecimal getAmount() { return amount; }
    public CardOperationType getType() { return cardOperationType; }
}