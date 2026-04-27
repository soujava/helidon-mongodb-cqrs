package com.acme.payment;

import jakarta.data.repository.BasicRepository;
import jakarta.data.repository.Repository;

import java.util.UUID;

@Repository
public interface PaymentRepository extends BasicRepository<Payment, UUID> {
}
