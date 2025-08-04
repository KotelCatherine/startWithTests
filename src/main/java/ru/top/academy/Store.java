package ru.top.academy;

public class Store {

    private int id;
    private String name;
    private String address;
    private boolean isOpen;

    public Store(int id, String name, String address, boolean isOpen) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.isOpen = isOpen;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public boolean isOpen() {
        return isOpen;
    }

    @Override
    public String toString() {
        return "Store{" +
                "name='" + name + '\'' +
                '}';
    }

}
