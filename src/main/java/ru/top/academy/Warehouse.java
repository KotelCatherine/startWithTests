package ru.top.academy;

public class Warehouse {

    private int storeId;
    private int productId;
    private int quantity;

    public Warehouse(int storeId, int productId, int quantity) {
        this.storeId = storeId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public int getStoreId() {
        return storeId;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "Warehouse{" +
                "storeId=" + storeId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }

}
