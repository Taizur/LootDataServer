package com.taizur.lootclient.csv;

import com.taizur.shared.model.LootItem;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class LootCsvReader {

    public String readLoot(Path csvPath) throws IOException {
        return Files.readString(csvPath);

    }
}
