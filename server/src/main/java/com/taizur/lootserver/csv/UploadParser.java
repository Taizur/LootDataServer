package com.taizur.lootserver.csv;

import com.taizur.shared.model.LootItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class UploadParser {

    public List<LootItem> parseUploadData(String lootData) throws IllegalArgumentException, IOException {
        List<LootItem> lootList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new StringReader(lootData))) {
            reader.readLine();
            String line;

            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");

                int id = Integer.parseInt(columns[0]);
                String name = columns[1].replace("\"", "");
                boolean tradeable = Boolean.parseBoolean(columns[2]);
                int quantity = Integer.parseInt(columns[3]);
                int gePrice = Integer.parseInt(columns[4]);
                LootItem item = new LootItem(id, name, tradeable, quantity, gePrice);
                lootList.add(item);
            }
        }
        return lootList;
    }
}
