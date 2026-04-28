package com.acme.cards;



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

    public Card() {
    }

    public Card(UUID cardId, BigDecimal availableBalance, CardStatus cardStatus) {
        this.cardId = cardId;
        this.availableBalance = availableBalance;
        this.cardStatus = cardStatus;
    }

    public boolean canAuthorize(BigDecimal amount) {
        return cardStatus == CardStatus.ACTIVE && availableBalance.compareTo(amount) >= 0;
    }

    public void debit(BigDecimal amount) {
        this.availableBalance = this.availableBalance.subtract(amount);
    }

    public UUID getCardId() { return cardId; }
    public BigDecimal getAvailableBalance() { return availableBalance; }
    public CardStatus getStatus() { return cardStatus; }
}