package com.acme.statistics;

import com.acme.Product;
import com.acme.infraestructure.JsonFieldStrategy;
import jakarta.json.bind.annotation.JsonbVisibility;
import jakarta.nosql.Column;
import jakarta.nosql.Entity;
import jakarta.nosql.Id;

import java.util.UUID;

@Entity
@JsonbVisibility(JsonFieldStrategy.class)
public class PaymentCounter {

    @Id
    private UUID productCode;

    @Column
    private Product product;

    @Column
    private int successfulPayments;

    @Column
    private int failedPayments;

    PaymentCounter(Product product) {
        this.productCode = product.code();
        this.product = product;
    }

    PaymentCounter() {
    }

    public void paymentFailed() {
        this.failedPayments++;
    }

    public void paymentSucceeded() {
        this.successfulPayments++;
    }

    @Override
    public String toString() {
        return "PaymentCounter{" +
                "productCode=" + productCode +
                ", successfulPayments=" + successfulPayments +
                ", failedPayments=" + failedPayments +
                '}';
    }
}