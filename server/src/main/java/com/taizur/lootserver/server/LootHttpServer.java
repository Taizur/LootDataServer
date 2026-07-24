package com.taizur.lootserver.server;
import com.sun.net.httpserver.HttpServer;
import com.taizur.lootserver.database.LootRepository;
import com.taizur.lootserver.handler.HealthHandler;
import com.taizur.lootserver.handler.UploadHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

public class LootHttpServer {
    private static final int PORT = 8080;
    private HttpServer myServer;

    public LootHttpServer(LootRepository repo) throws IOException {
        myServer = HttpServer.create(new InetSocketAddress(PORT), 0);
        myServer.createContext("/health", new HealthHandler());
        myServer.createContext("/upload", new UploadHandler(repo));
    }

    public void start() {
        myServer.start();
        System.out.println("Sever started on port " + PORT + ".");
    }
}
