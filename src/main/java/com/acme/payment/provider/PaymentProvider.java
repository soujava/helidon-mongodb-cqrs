package com.acme.payment.provider;

import com.acme.payment.PaymentFailedEvent;
import com.acme.payment.PaymentRequestedEvent;
import com.acme.payment.PaymentConfirmedEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.ObservesAsync;
import jakarta.inject.Inject;

import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@ApplicationScoped
class PaymentProvider {

    private static final Logger LOGGER = Logger.getLogger(PaymentProvider.class.getName());

    private final AtomicLong counter = new AtomicLong();

    private final Event<PaymentConfirmedEvent> successEvent;

    private final Event<PaymentFailedEvent> errorEvent;

    @Inject
    PaymentProvider(Event<PaymentConfirmedEvent> successEvent, Event<PaymentFailedEvent> errorEvent) {
        this.successEvent = successEvent;
        this.errorEvent = errorEvent;
    }

    PaymentProvider() {
        this.successEvent = null;
        this.errorEvent = null;
    }

    void receivePayment(@ObservesAsync PaymentRequestedEvent event) {
        LOGGER.info("Processing payment: " + event.payment().getId());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        if(counter.get() % 2 == 0) {
            LOGGER.warning("Payment failed: " + event.payment().getId());
            errorEvent.fire(new PaymentFailedEvent(event.payment()));
        } else {
            LOGGER.info("Payment successful: " + event.payment().getId());
            successEvent.fire(new PaymentConfirmedEvent(event.payment()));
        }
        LOGGER.info("Payment processed: " + event.payment().getId() + " - Confirmation #" + counter.incrementAndGet());
    }
}
