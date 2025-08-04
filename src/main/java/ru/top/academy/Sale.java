package ru.top.academy;

import java.time.LocalDate;

public class Sale {

    private int id;
    private int storeId;
    private int productId;
    private int quantity;
    private LocalDate date;

    public Sale(int id, int storeId, int productId, int quantity, String date) {
        this.id = id;
        this.storeId = storeId;
        this.productId = productId;
        this.quantity = quantity;
        this.date = LocalDate.parse(date);
    }

    public int getId() {
        return id;
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

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "id=" + id +
                ", storeId=" + storeId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", date=" + date +
                '}';
    }
}
