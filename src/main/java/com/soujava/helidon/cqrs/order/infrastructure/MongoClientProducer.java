package com.soujava.helidon.cqrs.order.infrastructure;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 * CDI producer that creates and exposes a {@link MongoClient} as an application-scoped bean.
 *
 * <p>Configuration is read from {@code microprofile-config.properties}:</p>
 * <ul>
 *   <li>{@code mongodb.uri} — MongoDB connection string (default: {@code mongodb://localhost:27017})</li>
 * </ul>
 */
@ApplicationScoped
public class MongoClientProducer {

    @Inject
    @ConfigProperty(name = "mongodb.uri", defaultValue = "mongodb://localhost:27017")
    private String mongoUri;

    @Produces
    @ApplicationScoped
    public MongoClient mongoClient() {
        return MongoClients.create(mongoUri);
    }
}
