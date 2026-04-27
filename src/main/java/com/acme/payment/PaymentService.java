package com.acme.payment;

import com.acme.Product;
import jakarta.data.Order;
import jakarta.data.Sort;
import jakarta.data.page.Page;
import jakarta.data.page.PageRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class PaymentService {

    private static final Logger LOGGER = Logger.getLogger(PaymentService.class.getName());
    private static final Order<Payment> PAYMENT_ORDER = Order.by(Sort.asc("id"));

    @Inject
    private PaymentRepository repository;

    @Inject
    private Event<PaymentRequestedEvent> event;

    public Payment create(PaymentRequest request) {

        LOGGER.info("Creating payment for " + request.productCode());
        Product product = new Product(
                request.productCode(),
                request.productName()
        );

        BigDecimal total = request.unitPrice()
                .multiply(BigDecimal.valueOf(request.quantity()));

        Payment payment = new Payment(
                product,
                total,
                PaymentStatus.PENDING
        );

        repository.save(payment);
        LOGGER.info("Payment created: " + payment);
        event.fireAsync(new PaymentRequestedEvent(payment));
        return payment;
    }

    public List<Payment> findAll(PageRequest pageRequest) {
        LOGGER.info("Finding all payments, page: " + pageRequest.page());
        Page<Payment> payments = repository.findAll(pageRequest, PAYMENT_ORDER);
        return payments.content();
    }
}