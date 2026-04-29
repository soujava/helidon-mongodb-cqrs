package com.acme.cards.query;

import jakarta.nosql.Column;
import jakarta.nosql.Entity;
import jakarta.nosql.Id;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
public record TransactionView (@Id
                               UUID id,

                                       @Column
                                       UUID cardId,

                                       @Column
                                       BigDecimal amount,

                                       @Column
                                       String status,

                                       @Column
                                       Instant createdAt) {


}