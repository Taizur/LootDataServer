package com.taizur.lootclient;


import com.taizur.lootclient.config.ClientConfig;
import com.taizur.lootclient.network.ServerClient;

import java.io.IOException;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        ClientConfig config = new ClientConfig();
        config.createDefaultConfig();
        config.loadConfig();
        System.out.println(config.getServerUrl());
        ServerClient serverClient = new ServerClient();
        System.out.println(serverClient.checkHealth(config));
    }

}