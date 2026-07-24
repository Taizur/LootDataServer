package com.taizur.lootserver.server;
import com.sun.net.httpserver.HttpServer;
import com.taizur.lootserver.handler.HealthHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

public class LootHttpServer {
    private static final int PORT = 8080;
    private HttpServer myServer;

    public LootHttpServer() throws IOException {
        myServer = HttpServer.create(new InetSocketAddress(PORT), 0);
        myServer.createContext("/health", new HealthHandler());
    }

    public void start() {
        myServer.start();
        System.out.println("Sever started on port " + PORT + ".");
    }
}
