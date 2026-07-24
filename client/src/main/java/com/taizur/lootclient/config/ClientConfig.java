package com.taizur.lootclient.config;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.Reader;
import java.util.Properties;

public class ClientConfig {
    private static final String CONFIG_PATH = "C:/LootDataClient/client.properties";
    private String serverAddress;
    private int serverPort;
    private String computerId;

    public void createDefaultConfig() throws IOException {
        Path configPath = Path.of(CONFIG_PATH);
        if (Files.exists(configPath)) {
            return;
        }

        Files.createDirectories(configPath.getParent());
        Files.createFile(configPath);

        String defaultConfig = """
                serverAddress=localhost
                serverPort=8080
                computerID=Laptop
                """;

        Files.writeString(configPath, defaultConfig);

    }

    public void loadConfig() throws IOException {
        Path configPath = Path.of(CONFIG_PATH);
        Properties properties = new Properties();
        try(Reader reader = Files.newBufferedReader(configPath)) {
            properties.load(reader);
        }

        serverAddress = properties.getProperty("serverAddress");
        computerId = properties.getProperty("computerID");
        serverPort = Integer.parseInt(properties.getProperty("serverPort"));
    }

    public String getServerAddress() { return serverAddress; }
    public String getComputerId() { return computerId; }
    public int getServerPort() { return serverPort; }
    public String getServerUrl() { return ("http://" + serverAddress + ":" + serverPort); }


}
