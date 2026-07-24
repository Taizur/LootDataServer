package com.taizur.lootserver;

import com.taizur.lootserver.database.LootRepository;
import com.taizur.lootserver.server.LootHttpServer;
import com.taizur.shared.model.LootItem;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            LootHttpServer myServer = new LootHttpServer();
            myServer.start();
        }
        catch(Exception e) {
            System.out.println("Failed to start Server.");
            e.printStackTrace();
        }

    }
}
