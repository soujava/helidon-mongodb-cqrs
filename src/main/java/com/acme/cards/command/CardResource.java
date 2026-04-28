package com.acme.cards.command;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.jnosql.mapping.document.DocumentTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@Path("/cards")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class CardResource {

    @Inject
    private DocumentTemplate template;

    @GET
    public List<Card> findAll() {
        List<Card> cards = template.select(Card.class).result();
        if (cards.isEmpty()) {
            seedCards();
            cards = template.select(Card.class).result();
        }
        return cards;
    }

    @GET
    @Path("/{id}")
    public Card findById(@PathParam("id") UUID id) {
        return template.find(Card.class, id)
                .orElseThrow(() -> new NotFoundException("Card not found"));
    }

    private void seedCards() {
        IntStream.range(0, 5)
                .mapToObj(i -> new Card(
                        UUID.randomUUID(),
                        new BigDecimal("1000"),
                        CardOperationStatus.ACTIVE
                ))
                .forEach(template::insert);
    }
}