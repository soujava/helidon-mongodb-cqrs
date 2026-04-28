package com.acme.cards.command;

import jakarta.nosql.Column;
import jakarta.nosql.Entity;
import jakarta.nosql.Id;

import java.time.Instant;
import java.util.UUID;

@Entity
public class OperationResult {

    @Id
    private UUID operationId;

    @Column
    private Status status;

    @Column
    private String reason;

    @Column
    private Instant processedAt;

    public OperationResult() {
    }

    public OperationResult(UUID operationId, Status status, String reason, Instant processedAt) {
        this.operationId = operationId;
        this.status = status;
        this.reason = reason;
        this.processedAt = processedAt;
    }

    public UUID getOperationId() { return operationId; }
    public Status getStatus() { return status; }
    public String getReason() { return reason; }
}