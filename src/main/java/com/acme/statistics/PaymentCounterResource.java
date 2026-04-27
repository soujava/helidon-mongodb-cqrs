package com.acme.statistics;

import com.acme.payment.Payment;
import jakarta.data.page.PageRequest;
import jakarta.inject.Inject;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;

import java.util.List;

@Path("/payment-counter")
public class PaymentCounterResource {

    private final PaymentCounterService service;

    @Inject
    public PaymentCounterResource(PaymentCounterService service) {
        this.service = service;
    }

    PaymentCounterResource() {
        this.service = null;
    }

    @GET
    public List<PaymentCounter> getAll(@QueryParam("page") @DefaultValue("1") int page) {
        var pageRequest = PageRequest.ofPage(page);
        return service.findAll(pageRequest);
    }

}
