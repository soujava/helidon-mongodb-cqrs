package com.acme.cards.query;

import jakarta.nosql.Column;
import jakarta.nosql.Entity;
import jakarta.nosql.Id;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
public class TransactionView {

    @Id
    private UUID transactionId;

    @Column
    private UUID cardId;

    @Column
    private BigDecimal amount;

    @Column
    private String status;

    @Column
    private Instant createdAt;

    public TransactionView() {
    }

    public TransactionView(UUID transactionId,
                           UUID cardId,
                           BigDecimal amount,
                           String status,
                           Instant createdAt) {
        this.transactionId = transactionId;
        this.cardId = cardId;
        this.amount = amount;
        this.status = status;
        this.createdAt = createdAt;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public UUID getCardId() {
        return cardId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }

}