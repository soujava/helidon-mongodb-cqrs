package com.acme.cards.command;

import com.acme.cards.query.TransactionView;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import jakarta.ws.rs.WebApplicationException;
import org.eclipse.jnosql.mapping.document.DocumentTemplate;

import java.time.Instant;
import java.util.UUID;
import java.util.logging.Logger;

import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;

@ApplicationScoped
public class AuthorizeCardCommandHandler {

    private static final Logger LOGGER = Logger.getLogger(AuthorizeCardCommandHandler.class.getName());

    private final DocumentTemplate template;

    @Inject
    public AuthorizeCardCommandHandler(DocumentTemplate template) {
        this.template = template;
    }

    AuthorizeCardCommandHandler() {
        this.template = null;
    }

    public OperationResult handle(AuthorizeCardCommand command) {

        LOGGER.info("Processing authorize command for cardId=" + command.cardId()
                + " amount=" + command.amount());

        var card = template.find(Card.class, command.cardId())
                .orElseThrow(() -> new WebApplicationException("Card not found, cardid=" + command.cardId(), NOT_FOUND));

        var operationId = UUID.randomUUID();

        if (!card.canAuthorize(command.amount())) {
            LOGGER.warning("Authorization declined for cardId=" + command.cardId());

            var result = new OperationResult(
                    operationId,
                    card.getId(),
                    OperationStatus.DECLINED,
                    "Insufficient balance or inactive card",
                    Instant.now()
            );

            template.insert(result);

            return result;
        }

        card.debit(command.amount());
        template.update(card);

        LOGGER.info("Authorization approved for cardId=" + command.cardId());

        var result = new OperationResult(
                operationId,
                card.getId(),
                OperationStatus.APPROVED,
                command.reason(),
                Instant.now()
        );

        template.insert(result);
        updateProjection(command, result);

        return result;
    }

    private void updateProjection(AuthorizeCardCommand command, OperationResult result) {

        LOGGER.info("Updating transaction view for id=" + result.id());

        var view = new TransactionView(
                result.id(),
                command.cardId(),
                command.amount(),
                result.status().name(),
                result.processedAt()
        );

        template.insert(view);
    }
}