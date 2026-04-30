package com.acme.cards.query;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.jnosql.mapping.document.DocumentTemplate;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@Path("/cards/{id}/transactions")
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class CardQueryResource {

    private static final Logger LOGGER = Logger.getLogger(CardQueryResource.class.getName());

    private final DocumentTemplate template;

    @Inject
    public CardQueryResource(DocumentTemplate template) {
        this.template = template;
    }

    CardQueryResource() {
        this.template = null;
    }

    @GET
    public List<TransactionView> findByCardId(@PathParam("id") UUID cardId) {

        LOGGER.info("Fetching transactions for cardId=" + cardId);

        return template.select(TransactionView.class)
                .where("cardId")
                .eq(cardId)
                .orderBy("createdAt")
                .desc()
                .result();
    }
}