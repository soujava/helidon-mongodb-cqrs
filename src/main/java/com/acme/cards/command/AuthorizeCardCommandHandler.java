package com.acme.cards.command;

import com.acme.cards.query.TransactionView;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.eclipse.jnosql.mapping.document.DocumentTemplate;

import java.time.Instant;
import java.util.UUID;
import java.util.logging.Logger;

@ApplicationScoped
public class AuthorizeCardCommandHandler {

    private static final Logger LOGGER = Logger.getLogger(AuthorizeCardCommandHandler.class.getName());

    @Inject
    private DocumentTemplate template;

    public OperationResult handle(AuthorizeCardCommand command) {

        LOGGER.info("Processing authorize command for cardId=" + command.cardId()
                + " amount=" + command.amount());

        var card = template.find(Card.class, command.cardId())
                .orElseThrow(() -> new IllegalArgumentException("Card not found"));

        var operationId = UUID.randomUUID();

        if (!card.canAuthorize(command.amount())) {
            LOGGER.warning("Authorization declined for cardId=" + command.cardId());

            var result = new OperationResult(
                    operationId,
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
                OperationStatus.APPROVED,
                null,
                Instant.now()
        );

        template.insert(result);
        updateProjection(command, result);

        return result;
    }

    private void updateProjection(AuthorizeCardCommand command, OperationResult result) {

        LOGGER.info("Updating transaction view for operationId=" + result.getId());

        var view = new TransactionView(
                result.getId(),
                command.cardId(),
                command.amount(),
                result.getStatus().name(),
                result.getProcessedAt()
        );

        template.insert(view);
    }
}