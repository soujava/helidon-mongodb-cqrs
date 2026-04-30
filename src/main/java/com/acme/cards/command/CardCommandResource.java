package com.acme.cards.command;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.UUID;
import java.util.logging.Logger;

@Path("/cards/{id}/authorize")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class CardCommandResource {

    private static final Logger LOGGER = Logger.getLogger(CardCommandResource.class.getName());

    @Inject
    private AuthorizeCardCommandHandler handler;

    @POST
    public OperationResult authorize(@PathParam("id") UUID cardId, AuthorizeRequest request) {

        LOGGER.info("Received authorize request for cardId=" + cardId);

        var command = new AuthorizeCardCommand(cardId, request.amount(), request.reason());
        return handler.handle(command);
    }

}