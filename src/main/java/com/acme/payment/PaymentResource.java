package com.acme.payment;

import jakarta.data.page.PageRequest;
import jakarta.inject.Inject;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/payments")
public class PaymentResource {


    private final PaymentService service;

    @Inject
    public PaymentResource(PaymentService service) {
        this.service = service;
    }

    PaymentResource() {
        this.service = null;
    }

    @POST
    public Response create(PaymentRequest request) {
        return Response.accepted(service.create(request)).build();
    }

    @GET
    public List<Payment> getAll(@QueryParam("page") @DefaultValue("1") int page) {
        var pageRequest = PageRequest.ofPage(page);
        return service.findAll(pageRequest);
    }
}