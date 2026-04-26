package com.soujava.helidon.cqrs.order.api;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Maps domain rule violations to appropriate HTTP responses.
 *
 * <ul>
 *   <li>{@link IllegalArgumentException} → 400 Bad Request (e.g. order not found, invalid input)</li>
 *   <li>{@link IllegalStateException}    → 409 Conflict    (e.g. confirming a non-pending order)</li>
 * </ul>
 */
@Provider
public class DomainExceptionMapper implements ExceptionMapper<RuntimeException> {

    private static final Logger LOGGER = Logger.getLogger(DomainExceptionMapper.class.getName());

    @Override
    public Response toResponse(RuntimeException exception) {
        if (exception instanceof IllegalArgumentException) {
            LOGGER.log(Level.FINE, "Bad request: {0}", exception.getMessage());
            return errorResponse(Response.Status.BAD_REQUEST, exception.getMessage());
        }
        if (exception instanceof IllegalStateException) {
            LOGGER.log(Level.FINE, "Conflict: {0}", exception.getMessage());
            return errorResponse(Response.Status.CONFLICT, exception.getMessage());
        }
        LOGGER.log(Level.SEVERE, "Unexpected error processing request", exception);
        return errorResponse(Response.Status.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
    }

    private Response errorResponse(Response.Status status, String message) {
        return Response.status(status)
                .type(MediaType.APPLICATION_JSON)
                .entity(Map.of("error", message))
                .build();
    }
}
