package com.acme.cards.command;

import com.acme.infraestructure.JsonFieldStrategy;
import jakarta.json.bind.annotation.JsonbVisibility;
import jakarta.nosql.Column;
import jakarta.nosql.Entity;
import jakarta.nosql.Id;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@JsonbVisibility(JsonFieldStrategy.class)
public class OperationResult {

    @Id
    private UUID id;

    @Column
    private OperationStatus status;

    @Column
    private String reason;

    @Column
    private Instant processedAt;

    OperationResult(UUID id, OperationStatus status, String reason, Instant processedAt) {
        this.id = id;
        this.status = status;
        this.reason = reason;
        this.processedAt = processedAt;
    }

    OperationResult() {
    }

    public UUID getId() {
        return id;
    }

    public OperationStatus getStatus() {
        return status;
    }

    public String getReason() {
        return reason;
    }

    public Instant getProcessedAt() {
        return processedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof OperationResult that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "OperationResult{" +
                "id=" + id +
                ", operationStatus=" + status +
                ", reason='" + reason + '\'' +
                ", processedAt=" + processedAt +
                '}';
    }
}