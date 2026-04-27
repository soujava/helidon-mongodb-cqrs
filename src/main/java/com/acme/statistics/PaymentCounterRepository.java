package com.acme.statistics;

import jakarta.data.repository.BasicRepository;
import jakarta.data.repository.Repository;

import java.util.UUID;

@Repository
public interface PaymentCounterRepository extends BasicRepository<PaymentCounter, UUID> {
}
