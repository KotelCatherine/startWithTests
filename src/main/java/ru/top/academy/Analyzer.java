package ru.top.academy;

import java.util.ArrayList;
import java.util.List;

public class Analyzer {

    private List<Store> stores = new ArrayList<>();

    public Analyzer(List<Store> stores) {
        this.stores = stores;
    }

    public List<Store> findOpenStoreOnBasseina() {
        return stores.stream()
                .filter(store -> store.isOpen() && (store.getAddress().contains("Бассейная")))
                .toList();
    }

}
