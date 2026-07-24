package com.taizur.lootserver.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.taizur.lootserver.csv.UploadParser;
import com.taizur.lootserver.database.LootRepository;
import com.taizur.shared.model.LootItem;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class UploadHandler implements HttpHandler  {
    private final LootRepository repo;

    public UploadHandler(LootRepository repo) {
        this.repo = repo;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
            sendResponse(exchange, 405, "This endpoint only accepts Post requests.");

            return;
        }


        try {
            String computerID = exchange.getRequestHeaders().getFirst("Computer-ID");

            String lootData = new String(
                    exchange.getRequestBody().readAllBytes(),
                    StandardCharsets.UTF_8
            );

            if ((computerID == null) || (computerID.isBlank())) {
                sendResponse(exchange, 400, "Missing computer ID");
            }

            if ((!computerID.equals("Main_PC")) && (!computerID.equals("Laptop"))) {
                sendResponse(exchange, 400, "Invalid Computer ID.");
            }

            if (lootData.isBlank()) {
                sendResponse(exchange, 400, "Loot data is empty, upload rejected.");
                return;
            }
            //create parser and parse the data
            UploadParser parser = new UploadParser();
            List<LootItem> lootItems = parser.parseUploadData(lootData);

            //validate that list is okay, update if applicable, send appropriate response
            if (repo.validateUpload(computerID, lootItems)) {
                repo.updateClientLootTable(computerID, lootItems);
                repo.updateMasterLootTable();
                sendResponse(exchange, 200, "Upload successful, database updated.");
            } else {
                sendResponse(exchange, 400, "Upload rejected, database was not updated.");
            }


        } catch (Exception e) {
            e.printStackTrace();
            sendResponse(exchange, 500, "Upload failed.");
        }

        }


    private void sendResponse(HttpExchange exchange, int statusCode, String responseText) throws IOException {
        byte[] responseBytes = responseText.getBytes(StandardCharsets.UTF_8);

        exchange.sendResponseHeaders(statusCode, responseBytes.length);

        try (OutputStream outputStream = exchange.getResponseBody()) {
            outputStream.write(responseBytes);
        }
    }
}

