package com.taizur.shared.model;

public class LootItem {
    private int itemID;
    private String itemName;
    private boolean tradeable;
    private int totalQuantity;
    private int gePrice;

    public LootItem (int itemID, String itemName, boolean tradeable, int totalQuantity, int gePrice) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.tradeable = tradeable;
        this.totalQuantity = totalQuantity;
        this.gePrice = gePrice;
    }

    public int getItemID() { return itemID; }
    public String getItemName() { return itemName; }
    public boolean isTradeable() { return tradeable; }
    public int getTotalQuantity() { return totalQuantity; }
    public int getGePrice() { return gePrice; }
}
