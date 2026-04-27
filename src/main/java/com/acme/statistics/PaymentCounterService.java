package com.acme.statistics;

import com.acme.Product;
import com.acme.payment.Payment;
import com.acme.payment.PaymentFailedEvent;
import com.acme.payment.PaymentConfirmedEvent;
import jakarta.data.Order;
import jakarta.data.Sort;
import jakarta.data.page.Page;
import jakarta.data.page.PageRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Logger;

@ApplicationScoped
public class PaymentCounterService {

    private static final Order<PaymentCounter> PRODUCT_CODE = Order.by(Sort.asc("productCode"));

    private static final Logger LOGGER = Logger.getLogger(PaymentCounterService.class.getName());

    private final PaymentCounterRepository repository;

    @Inject
    PaymentCounterService(PaymentCounterRepository repository) {
        this.repository = repository;
    }

    PaymentCounterService() {
        this.repository = null;
    }

    void success(@Observes PaymentConfirmedEvent event) {
        LOGGER.info("Payment successful, incrementing counter: " + event.payment().getId());
        execute(PaymentCounter::paymentSucceeded, event.payment().getProduct());
    }


    void success(@Observes PaymentFailedEvent event) {
        LOGGER.info("Payment failed, incrementing counter: " + event.payment().getId());
        execute(PaymentCounter::paymentFailed, event.payment().getProduct());
    }

    void execute(Consumer<PaymentCounter> action, Product product) {
        PaymentCounter counter = repository.findById(product.code()).orElseGet(() -> new PaymentCounter(product));
        LOGGER.info("Counter found: " + counter);
        action.accept(counter);
        LOGGER.info("Counter incremented: " + counter);
        repository.save(counter);
    }

    public List<PaymentCounter> findAll(PageRequest pageRequest) {
        LOGGER.info("Finding all counters, page: " + pageRequest.page());
        Page<PaymentCounter> counters = repository.findAll(pageRequest, PRODUCT_CODE);
        return counters.content();
    }
}
