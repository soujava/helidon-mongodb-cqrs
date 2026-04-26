package com.soujava.helidon.cqrs.shared.domain;

import java.time.Instant;
import java.util.UUID;

/**
 * Base class for all domain events.
 * A domain event represents something meaningful that happened in the domain.
 */
public abstract class DomainEvent {

    private final String eventId;
    private final Instant occurredOn;

    protected DomainEvent() {
        this.eventId = UUID.randomUUID().toString();
        this.occurredOn = Instant.now();
    }

    public String getEventId() {
        return eventId;
    }

    public Instant getOccurredOn() {
        return occurredOn;
    }
}
