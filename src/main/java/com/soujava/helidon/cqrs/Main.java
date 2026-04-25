package com.soujava.helidon.cqrs;

import io.helidon.microprofile.server.Server;

/**
 * Application entry point. Bootstraps the Helidon MicroProfile server,
 * which in turn starts the CDI container and registers all JAX-RS resources.
 */
public class Main {

    private Main() {
    }

    public static void main(String[] args) {
        Server.create().start();
    }
}
