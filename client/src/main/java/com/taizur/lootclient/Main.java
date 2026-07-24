package com.taizur.lootclient;


import com.taizur.lootclient.config.ClientConfig;
import com.taizur.lootclient.csv.LootCsvReader;
import com.taizur.lootclient.network.ServerClient;
import com.taizur.shared.model.LootItem;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Path csvPath = Path.of("C:/Users/brand/.runelite/taizurs-loot-logger/loot-log.csv");
        LootCsvReader reader = new LootCsvReader();
        ClientConfig config = new ClientConfig();
        config.createDefaultConfig();
        config.loadConfig();
        ServerClient serverClient = new ServerClient();
        String lootData = reader.readLoot(csvPath);

        System.out.println(serverClient.uploadData(config, lootData));



    }

}