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
    private UUID id;

    @Column
    private UUID cardId;

    @Column
    private BigDecimal amount;

    @Column
    private String status;

    @Column
    private Instant createdAt;


    public TransactionView(UUID id, UUID cardId, BigDecimal amount, String status, Instant createdAt) {
        this.id = id;
        this.cardId = cardId;
        this.amount = amount;
        this.status = status;
        this.createdAt = createdAt;
    }

    public TransactionView() {
    }
}