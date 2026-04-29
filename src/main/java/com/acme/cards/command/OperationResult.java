package com.acme.cards.command;

import jakarta.nosql.Column;
import jakarta.nosql.Entity;
import jakarta.nosql.Id;

import java.time.Instant;
import java.util.UUID;

@Entity
public record OperationResult (@Id UUID id, @Column  OperationStatus status, @Column String reason, @Column Instant processedAt){
}