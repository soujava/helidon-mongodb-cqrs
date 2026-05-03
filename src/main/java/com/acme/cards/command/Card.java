package com.acme.cards.command;

import com.acme.cards.infraestructure.JsonFieldStrategy;
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
    private UUID id;

    @Column
    private BigDecimal availableBalance;

    @Column
    private CardOperationStatus status;

    Card() {
    }

    public Card(UUID id, BigDecimal availableBalance, CardOperationStatus status) {
        this.id = id;
        this.availableBalance = availableBalance;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public CardOperationStatus getStatus() {
        return status;
    }

    /**
     * Determines whether a card can authorize a transaction for the given amount.
     * The authorization is possible only if the card is active and the available
     * balance is greater than or equal to the specified amount.
     *
     * @param amount the transaction amount to be authorized
     * @return true if the card is active and has sufficient available balance
     *         to authorize the transaction, false otherwise
     */
    public boolean canAuthorize(BigDecimal amount) {
        return status == CardOperationStatus.ACTIVE
                && availableBalance.compareTo(amount) >= 0;
    }

    public void debit(BigDecimal amount) {
        this.availableBalance = this.availableBalance.subtract(amount);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Card card)) {
            return false;
        }
        return Objects.equals(id, card.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", availableBalance=" + availableBalance +
                ", cardOperationStatus=" + status +
                '}';
    }
}