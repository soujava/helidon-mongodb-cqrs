package com.acme.cards.command;



import com.acme.infraestructure.JsonFieldStrategy;
import jakarta.json.bind.annotation.JsonbVisibility;
import jakarta.nosql.Column;
import jakarta.nosql.Entity;
import jakarta.nosql.Id;

import java.math.BigDecimal;
import java.util.Objects;
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

    Card() {
    }

    public Card(UUID cardId, BigDecimal availableBalance, CardOperationStatus cardOperationStatus) {
        this.cardId = cardId;
        this.availableBalance = availableBalance;
        this.cardOperationStatus = cardOperationStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Card card)) {
            return false;
        }
        return Objects.equals(cardId, card.cardId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cardId);
    }

    @Override
    public String toString() {
        return "Card{" +
                "cardId=" + cardId +
                ", availableBalance=" + availableBalance +
                ", cardOperationStatus=" + cardOperationStatus +
                '}';
    }
}