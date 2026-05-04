package com.acme.cards.command;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import jakarta.ws.rs.core.Response;
import org.eclipse.jnosql.mapping.document.DocumentTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.IntStream;

@Path("/cards")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class CardResource {

    private static final Logger LOGGER = Logger.getLogger(CardResource.class.getName());
    private static final BigDecimal INITIAL_BALANCE = new BigDecimal("1000");

    private final DocumentTemplate template;

    @Inject
    public CardResource(DocumentTemplate template) {
        this.template = template;
    }

    CardResource() {
        this.template = null;
    }

    @GET
    public List<Card> findAll() {

        LOGGER.info("Fetching all cards");

        List<Card> cards = template.select(Card.class).result();
        if (cards.isEmpty()) {
            LOGGER.warning("No cards found. Seeding initial dataset...");
            seedCards();
            cards = template.select(Card.class).result();
        }
        LOGGER.info("Returning " + cards.size() + " cards");
        return cards;
    }

    @GET
    @Path("/{id}")
    public Card findById(@PathParam("id") UUID id) {

        LOGGER.info("Fetching card with id=" + id);

        return template.find(Card.class, id).orElseThrow(() ->
                   new WebApplicationException("Card not found with the id: " + id, Response.Status.NOT_FOUND)
                );
    }

    private void seedCards() {

        IntStream.range(0, 5)
                .mapToObj(i -> new Card(
                        UUID.randomUUID(),
                        INITIAL_BALANCE,
                        CardOperationStatus.ACTIVE
                ))
                .forEach(card -> {
                    template.insert(card);
                    LOGGER.fine("Seeded card with id=" + card.getId()
                            + " and balance=" + card.getAvailableBalance());
                });

        LOGGER.info("Finished seeding cards");
    }
}