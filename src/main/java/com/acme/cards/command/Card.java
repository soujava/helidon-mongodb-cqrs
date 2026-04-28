package com.acme.cards.command;



import jakarta.nosql.Column;
import jakarta.nosql.Entity;
import jakarta.nosql.Id;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class Card {

    @Id
    private UUID cardId;

    @Column
    private BigDecimal availableBalance;

    @Column
    private CardStatus cardStatus;

}