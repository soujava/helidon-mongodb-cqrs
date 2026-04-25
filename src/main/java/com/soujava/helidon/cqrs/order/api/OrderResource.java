package com.soujava.helidon.cqrs.order.api;

import com.soujava.helidon.cqrs.order.command.CancelOrderCommand;
import com.soujava.helidon.cqrs.order.command.ConfirmOrderCommand;
import com.soujava.helidon.cqrs.order.command.CreateOrderCommand;
import com.soujava.helidon.cqrs.order.query.FindAllOrdersQuery;
import com.soujava.helidon.cqrs.order.query.FindOrderByIdQuery;
import com.soujava.helidon.cqrs.order.query.OrderView;
import com.soujava.helidon.cqrs.shared.command.CommandBus;
import com.soujava.helidon.cqrs.shared.query.QueryBus;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST resource for the Order feature.
 *
 * <p>All write operations are dispatched through the {@link CommandBus};
 * all read operations are dispatched through the {@link QueryBus}.
 * The resource itself is free of business logic.</p>
 *
 * <pre>
 * POST   /orders              – place a new order
 * GET    /orders              – list all orders
 * GET    /orders/{id}         – get a single order
 * PUT    /orders/{id}/confirm – confirm a pending order
 * PUT    /orders/{id}/cancel  – cancel an order
 * </pre>
 */
@Path("/orders")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    private final CommandBus commandBus;
    private final QueryBus queryBus;

    @Inject
    public OrderResource(CommandBus commandBus, QueryBus queryBus) {
        this.commandBus = commandBus;
        this.queryBus = queryBus;
    }

    @POST
    public Response createOrder(CreateOrderRequest request) {
        List<CreateOrderCommand.OrderItemData> items = request.getItems().stream()
                .map(item -> new CreateOrderCommand.OrderItemData(
                        item.getProductId(),
                        item.getQuantity(),
                        item.getUnitPrice()))
                .collect(Collectors.toList());

        String orderId = commandBus.dispatch(
                new CreateOrderCommand(request.getCustomerId(), items));

        return Response.created(URI.create("/orders/" + orderId))
                .entity(orderId)
                .build();
    }

    @GET
    public List<OrderView> getAllOrders() {
        return queryBus.dispatch(new FindAllOrdersQuery());
    }

    @GET
    @Path("/{id}")
    public Response getOrderById(@PathParam("id") String id) {
        OrderView view = queryBus.dispatch(new FindOrderByIdQuery(id));
        if (view == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(view).build();
    }

    @PUT
    @Path("/{id}/confirm")
    public Response confirmOrder(@PathParam("id") String id) {
        commandBus.dispatch(new ConfirmOrderCommand(id));
        return Response.noContent().build();
    }

    @PUT
    @Path("/{id}/cancel")
    public Response cancelOrder(@PathParam("id") String id) {
        commandBus.dispatch(new CancelOrderCommand(id));
        return Response.noContent().build();
    }
}
