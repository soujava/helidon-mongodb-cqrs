package com.acme.cards.command;

import com.acme.infraestructure.JsonFieldStrategy;
import jakarta.json.bind.annotation.JsonbVisibility;
import jakarta.nosql.Column;
import jakarta.nosql.Entity;
import jakarta.nosql.Id;

import java.time.Instant;
import java.util.UUID;

@Entity
@JsonbVisibility(JsonFieldStrategy.class)
public class OperationResult {

    @Id
    private UUID operationId;

    @Column
    private OperationStatus operationStatus;

    @Column
    private String reason;

    @Column
    private Instant processedAt;

}