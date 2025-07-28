package ru.top.academy;

import java.util.Arrays;
import java.util.List;

public class StoreRepo {

  private final List<Store> stores = Arrays.asList(
            new Store(1, "Пятерочка", "ул. Бассейная, 10", true),

            new Store(2, "Магнит", "ул. Ленина, 5", true),

            new Store(3, "Перекресток", "ул. Бассейная, 15", false),

            new Store(4, "Дикси", "ул. Гагарина, 20", true)
    );

    public List<Store> getStores() {
        return stores;
    }

}
