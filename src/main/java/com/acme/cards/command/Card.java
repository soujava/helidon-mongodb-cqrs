package com.acme.cards.command;



import com.acme.infraestructure.JsonFieldStrategy;
import jakarta.json.bind.annotation.JsonbVisibility;
import jakarta.nosql.Column;
import jakarta.nosql.Entity;
import jakarta.nosql.Id;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@JsonbVisibility(JsonFieldStrategy.class)
public class Card {

    @Id
    private UUID cardId;

    @Column
    private BigDecimal availableBalance;

    @Column
    private CardOperationStatus cardOperationStatus;

}