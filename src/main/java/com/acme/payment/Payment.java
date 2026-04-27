package com.acme.payment;

import com.acme.Product;
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
public class Payment {

    @Id
    private UUID id;

    @Column
    private BigDecimal amount;

    @Column
    private PaymentStatus status;

    @Column
    private Product product;

    Payment() {
    }

    Payment(Product product, BigDecimal amount, PaymentStatus status) {
        this.id = UUID.randomUUID();
        this.amount = amount;
        this.status = status;
        this.product = product;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Payment payment)) {
            return false;
        }
        return Objects.equals(id, payment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public Product getProduct() {
        return product;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id='" + id + '\'' +
                ", amount=" + amount +
                ", status=" + status +
                ", product=" + product +
                '}';
    }

    public void failed() {
        this.status = PaymentStatus.FAILED;
    }

    public void confirmed() {
        this.status = PaymentStatus.CONFIRMED;
    }
}