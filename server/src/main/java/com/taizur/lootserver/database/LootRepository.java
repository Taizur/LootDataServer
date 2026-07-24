package com.taizur.lootserver.database;
import com.taizur.shared.model.LootItem;

import java.sql.*;
import java.util.List;
import java.util.Map;

public class LootRepository {
    private static final String DATABASE_URL = "jdbc:sqlite:C:/LootDataServer/Data/loot.db";

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL);
    }

    public void createClientLootTable() throws SQLException {
        String sql = """
                CREATE TABLE IF NOT EXISTS client_loot (
                    computer_id TEXT NOT NULL,
                    item_id INTEGER NOT NULL,
                    item_name TEXT NOT NULL,
                    tradeable INTEGER NOT NULL,
                    total_quantity INTEGER NOT NULL,
                    ge_price INTEGER NOT NULL,
                    PRIMARY KEY (computer_id, item_id)
                );
                """;

        try (Connection connection = connect();
             Statement statement = connection.createStatement()) {

            statement.execute(sql);
        }
    }

    public void createMasterLootTable() throws SQLException {
        String sql = """
                CREATE TABLE IF NOT EXISTS master_loot (
                    item_id INTEGER NOT NULL,
                    item_name TEXT NOT NULL,
                    tradeable INTEGER NOT NULL,
                    total_quantity INTEGER NOT NULL,
                    ge_price INTEGER NOT NULL,
                    PRIMARY KEY (item_id)
                );
                """;

        try (Connection connection = connect();
             Statement statement = connection.createStatement()) {

            statement.execute(sql);
        }


    }

    public void updateClientLootTable(String computerId, List<LootItem> lootItems) throws SQLException {

        String sql = """
                INSERT INTO client_loot (
                    computer_id,
                    item_id,
                    item_name,
                    tradeable,
                    total_quantity,
                    ge_price
                )
                VALUES (?, ?, ?, ?, ?, ?)
                ON CONFLICT(computer_id, item_id)
                DO UPDATE SET
                    item_name = excluded.item_name,
                    tradeable = excluded.tradeable,
                    total_quantity = excluded.total_quantity,
                    ge_price = excluded.ge_price;
                """;

        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            connection.setAutoCommit(false);

            try {
                for (LootItem item : lootItems) {
                    statement.setString(1, computerId);
                    statement.setInt(2, item.getItemID());
                    statement.setString(3, item.getItemName());
                    statement.setBoolean(4, item.isTradeable());
                    statement.setInt(5, item.getTotalQuantity());
                    statement.setInt(6, item.getGePrice());

                    statement.addBatch();
                }

                statement.executeBatch();
                connection.commit();

            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        }
    }

    //validates that upload is a good file, protects database from accidental wipe.
    public boolean validateUpload(String computerID, List<LootItem> lootItems) throws SQLException {
        String sql = """
                SELECT item_id, total_quantity
                FROM client_loot
                WHERE computer_id = ?;
                """;
        try (Connection connection = connect(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, computerID);


            try (ResultSet results = statement.executeQuery()) {
                while (results.next()) {
                    int storedItemID = results.getInt("item_id");
                    int storedQuantity = results.getInt("total_quantity");

                    boolean matchFound = false;

                    for (LootItem uploadedItem : lootItems) {
                        if (uploadedItem.getItemID() == storedItemID) {
                            matchFound = true;

                            if (uploadedItem.getTotalQuantity() < storedQuantity) {
                                return false;
                            }

                            break;
                        }
                    }
                    if (!matchFound) {
                        return false;
                    }
                }
                return true;
            }
        }
    }


}
