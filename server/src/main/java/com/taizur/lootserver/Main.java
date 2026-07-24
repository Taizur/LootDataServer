package com.taizur.lootserver;

import com.taizur.lootserver.database.LootRepository;
import com.taizur.lootserver.handler.UploadHandler;
import com.taizur.lootserver.server.LootHttpServer;
import com.taizur.shared.model.LootItem;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        LootRepository repo = new LootRepository();

        try {
            repo.createMasterLootTable();
            repo.createClientLootTable();
            LootHttpServer myServer = new LootHttpServer(repo);
            myServer.start();
        }
        catch(Exception e) {
            System.out.println("Server startup failed.");
            e.printStackTrace();
        }
    }
}
