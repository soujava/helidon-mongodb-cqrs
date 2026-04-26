package com.soujava.helidon.cqrs.order.infrastructure;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.soujava.helidon.cqrs.order.domain.Order;
import com.soujava.helidon.cqrs.order.domain.OrderId;
import com.soujava.helidon.cqrs.order.domain.OrderItem;
import com.soujava.helidon.cqrs.order.domain.OrderRepository;
import com.soujava.helidon.cqrs.order.domain.OrderStatus;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.bson.Document;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.mongodb.client.model.ReplaceOptions;

import static com.mongodb.client.model.Filters.eq;

/**
 * MongoDB implementation of {@link OrderRepository}.
 *
 * <p>Each {@link Order} is stored as a single BSON document in the
 * {@code orders} collection of the configured database.</p>
 *
 * <p>Configuration property: {@code mongodb.database} (default: {@code orders_db})</p>
 */
@ApplicationScoped
public class MongoOrderRepository implements OrderRepository {

    private static final String COLLECTION_NAME = "orders";

    private final MongoClient mongoClient;

    @Inject
    @ConfigProperty(name = "mongodb.database", defaultValue = "orders_db")
    private String databaseName;

    @Inject
    public MongoOrderRepository(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    @Override
    public void save(Order order) {
        Document doc = toDocument(order);
        collection().replaceOne(
                eq("_id", order.getId().getValue()),
                doc,
                new ReplaceOptions().upsert(true));
    }

    @Override
    public Optional<Order> findById(OrderId id) {
        Document doc = collection().find(eq("_id", id.getValue())).first();
        return Optional.ofNullable(doc).map(this::fromDocument);
    }

    @Override
    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        collection().find().forEach(doc -> orders.add(fromDocument(doc)));
        return orders;
    }

    private MongoCollection<Document> collection() {
        MongoDatabase database = mongoClient.getDatabase(databaseName);
        return database.getCollection(COLLECTION_NAME);
    }

    private Document toDocument(Order order) {
        List<Document> items = order.getItems().stream()
                .map(item -> new Document()
                        .append("productId", item.getProductId())
                        .append("quantity", item.getQuantity())
                        .append("unitPrice", item.getUnitPrice().toPlainString()))
                .collect(Collectors.toList());

        return new Document()
                .append("_id", order.getId().getValue())
                .append("customerId", order.getCustomerId())
                .append("status", order.getStatus().name())
                .append("items", items);
    }

    private Order fromDocument(Document doc) {
        List<Document> itemDocs = doc.getList("items", Document.class);
        List<OrderItem> items = itemDocs.stream()
                .map(item -> new OrderItem(
                        item.getString("productId"),
                        item.getInteger("quantity"),
                        new BigDecimal(item.getString("unitPrice"))))
                .collect(Collectors.toList());

        return Order.reconstitute(
                OrderId.of(doc.getString("_id")),
                doc.getString("customerId"),
                items,
                OrderStatus.valueOf(doc.getString("status")));
    }
}
