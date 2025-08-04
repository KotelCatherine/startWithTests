import org.junit.jupiter.api.Test;
import ru.top.academy.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class AnalyzerTest {

    private Analyzer analyzer = new Analyzer();

    @Test
    void findOpenStoreOnBasseina_whenEmptyList_thenReturnEmptyList() {

        List<Store> stores = new ArrayList<>();

        List<Store> openStores = analyzer.findOpenStoreOnBasseina(stores);

        assertTrue(openStores.isEmpty());

    }

    @Test
    void findOpenStoreOnBasseina_whenNoMatchingStores_thenReturnEmptyList() {

        List<Store> stores = List.of(
                new Store(3, "Перекресток", "ул. Димитрова, 15", false),
                new Store(4, "Дикси", "ул. Гагарина, 20", true));

        List<Store> openStores = analyzer.findOpenStoreOnBasseina(stores);

        assertTrue(openStores.isEmpty());

    }

    @Test
    void findOpenStoreOnBasseina_whenOneOpenStore_thenReturnThisStore() {

        List<Store> stores = List.of(
                new Store(1, "Пятерочка", "ул. Бассейная, 10", true),
                new Store(2, "Магнит", "ул. Ленина, 5", true)
        );

        List<Store> openStores = analyzer.findOpenStoreOnBasseina(stores);

        assertEquals(1, openStores.size());
        assertEquals(stores.get(0).getId(), openStores.get(0).getId());

    }

    @Test
    void findOpenStoreOnBasseina_whenMultipleOpenStores_thenReturnAll() {

        Store firstStore = new Store(1, "Пятерочка", "ул. Бассейная, 10", true);
        Store secondStore = new Store(2, "Магнит", "ул. Бассейная, 5", true);
        List<Store> stores = List.of(
                firstStore,
                secondStore,
                new Store(2, "Магнит", "ул. Ленина, 5", true)
        );

        List<Store> openStores = analyzer.findOpenStoreOnBasseina(stores);

        assertEquals(2, openStores.size());
        assertTrue(openStores.containsAll(List.of(firstStore, secondStore)));

    }

    @Test
    void findOpenStoreOnBasseina_whenOneStoreClosedAndOneStoreOpenAndBothOnBasseina_thenExcludedClosed() {

        Store firstStore = new Store(1, "Пятерочка", "ул. Бассейная, 10", false);
        Store secondStore = new Store(2, "Магнит", "ул. Бассейная, 5", true);
        List<Store> stores = List.of(
                firstStore,
                secondStore
        );

        List<Store> openStores = analyzer.findOpenStoreOnBasseina(stores);

        assertEquals(1, openStores.size());
        assertEquals(secondStore.getName(), openStores.get(0).getName());

    }

    @Test
    void calculateTotalStockTomatoesInAllWarehouses_whenEmptyList_thenReturnNull() {

        List<Warehouse> warehouses = new ArrayList<>();

        int quantity = analyzer.calculateTotalStockTomatoesInAllWarehouses(warehouses, 1);

        assertEquals(0, quantity);

    }

    @Test
    void calculateTotalStockTomatoesInAllWarehouses_whenNoTomatoesAnywhere_thenReturnNull() {

        List<Warehouse> warehouses = Arrays.asList(
                new Warehouse(1, 4, 50),  // В магазине 1 (Пятерочка) 50 молока
                new Warehouse(1, 2, 30),   // Огурцов 30
                new Warehouse(2, 2, 20),   // В Магните 20 огурцов
                new Warehouse(3, 3, 100),  // В Перекрестке 100 яблок
                new Warehouse(4, 4, 40)    // В Дикси 40 молока
        );

        int quantity = analyzer.calculateTotalStockTomatoesInAllWarehouses(warehouses, 1);

        assertEquals(0, quantity);

    }

    @Test
    void calculateTotalStockTomatoesInAllWarehouses_whenTomatoesAvailableInOneWarehouse_thenReturnQuantity() {

        List<Warehouse> warehouses = Arrays.asList(
                new Warehouse(1, 1, 50),  // В магазине 1 (Пятерочка) 50 помидоров
                new Warehouse(1, 2, 30),   // Огурцов 30
                new Warehouse(2, 2, 20),   // В Магните 20 огурцов
                new Warehouse(3, 3, 100),  // В Перекрестке 100 яблок
                new Warehouse(4, 4, 40)    // В Дикси 40 молока
        );

        int quantity = analyzer.calculateTotalStockTomatoesInAllWarehouses(warehouses, 1);

        assertEquals(50, quantity);

    }

    @Test
    void calculateTotalStockTomatoesInAllWarehouses_whenTomatoesAvailableInSeveralWarehouses_thenReturnQuantity() {

        List<Warehouse> warehouses = Arrays.asList(
                new Warehouse(1, 1, 50),  // В магазине 1 (Пятерочка) 50 помидоров
                new Warehouse(1, 2, 30),   // Огурцов 30
                new Warehouse(2, 1, 20),   // В Магните 20 помидоров
                new Warehouse(3, 3, 100),  // В Перекрестке 100 яблок
                new Warehouse(4, 4, 40)    // В Дикси 40 молока
        );

        int quantity = analyzer.calculateTotalStockTomatoesInAllWarehouses(warehouses, 1);

        assertEquals(70, quantity);

    }

    @Test
    void findProductWhichLessFifty_whenEmptyList_thenReturnNull() {

        Map<String, Integer> product = analyzer.findProductWhichLessFifty(new ArrayList<>(), new ArrayList<>());

        assertTrue(product.isEmpty());

    }

    @Test
    void findProductWhichLessFifty_whenThereAreTwoProducts_thenReturn() {

        Product tomatoes = new Product(1, "Помидоры", "Овощи", 120.0);
        Product pickle = new Product(2, "Огурцы", "Овощи", 90.0);
        List<Product> products = Arrays.asList(
                tomatoes,
                pickle,
                new Product(3, "Яблоки", "Фрукты", 80.0),
                new Product(4, "Молоко", "Молочные продукты", 70.0)
        );

        List<Warehouse> warehouses = Arrays.asList(
                new Warehouse(1, 1, 50),  // В магазине 1 (Пятерочка) 50 помидоров
                new Warehouse(1, 2, 30),   // Огурцов 30
                new Warehouse(2, 1, 20),   // В Магните 20 помидоров
                new Warehouse(3, 3, 100),  // В Перекрестке 100 яблок
                new Warehouse(4, 4, 50)    // В Дикси 40 молока
        );

        Map<String, Integer> product = analyzer.findProductWhichLessFifty(products, warehouses);

        assertTrue(product.containsKey(tomatoes.getName()));
        assertTrue(product.containsKey(pickle.getName()));
        assertEquals(20, product.get(tomatoes.getName()));
        assertEquals(30, product.get(pickle.getName()));

    }

    @Test
    void findProductWhichLessFifty_whenThereAreNoSuitableProducts_thenReturnNull() {

        List<Product> products = Arrays.asList(
                new Product(1, "Помидоры", "Овощи", 120.0),
                new Product(2, "Огурцы", "Овощи", 90.0),
                new Product(3, "Яблоки", "Фрукты", 80.0),
                new Product(4, "Молоко", "Молочные продукты", 70.0)
        );

        List<Warehouse> warehouses = Arrays.asList(
                new Warehouse(1, 1, 50),  // В магазине 1 (Пятерочка) 50 помидоров
                new Warehouse(1, 2, 50),   // Огурцов 50
                new Warehouse(2, 1, 50),   // В Магните 50 помидоров
                new Warehouse(3, 3, 100),  // В Перекрестке 100 яблок
                new Warehouse(4, 4, 50)    // В Дикси 50 молока
        );

        Map<String, Integer> product = analyzer.findProductWhichLessFifty(products, warehouses);

        assertTrue(product.isEmpty());

    }

    @Test
    void findProductWhichLessFifty_whenThereAreSuitableProductsWithSameName_thenReturnOneWithLeastQuantity() {

        Product tomatoes = new Product(1, "Помидоры", "Овощи", 120.0);
        List<Product> products = Arrays.asList(
                tomatoes,
                new Product(2, "Огурцы", "Овощи", 90.0),
                new Product(3, "Яблоки", "Фрукты", 80.0),
                new Product(4, "Молоко", "Молочные продукты", 70.0)
        );

        List<Warehouse> warehouses = Arrays.asList(
                new Warehouse(1, 1, 20),  // В магазине 1 (Пятерочка) 50 помидоров
                new Warehouse(1, 2, 50),   // Огурцов 50
                new Warehouse(2, 1, 40),   // В Магните 50 помидоров
                new Warehouse(3, 3, 100),  // В Перекрестке 100 яблок
                new Warehouse(4, 4, 50)    // В Дикси 50 молока
        );

        Map<String, Integer> product = analyzer.findProductWhichLessFifty(products, warehouses);

        assertEquals(1, product.size());
        assertTrue(product.containsKey(tomatoes.getName()));
        assertEquals(20, product.get(tomatoes.getName()));

    }

    @Test
    void getTopThreeSellingProducts_whenEmptyList_thenReturnNull() {

        List<String> topThree = analyzer.getTopThreeSellingProducts(new ArrayList<>(), new ArrayList<>());

        assertTrue(topThree.isEmpty());

    }

    @Test
    void getTopThreeSellingProducts_whenNumberProductsSoldIsSame_thenReturnFirstThree() {

        Product tomatoes = new Product(1, "Помидоры", "Овощи", 120.0);
        Product pickle = new Product(2, "Огурцы", "Овощи", 90.0);
        Product apple = new Product(3, "Яблоки", "Фрукты", 80.0);
        List<Product> products = Arrays.asList(
                tomatoes,
                pickle,
                apple,
                new Product(4, "Молоко", "Молочные продукты", 70.0)
        );

        List<Sale> sales = Arrays.asList(
                new Sale(1, 1, 1, 5, "2024-05-01"),  // В Пятерочке продано 5 помидоров
                new Sale(2, 1, 2, 6, "2024-05-01"),  // 3 огурца
                new Sale(3, 2, 1, 1, "2024-05-02"),  // В Магните 2 помидора
                new Sale(4, 3, 3, 6, "2024-05-03"),  // В Перекрестке 10 яблок
                new Sale(5, 3, 4, 6, "2024-05-03")  // В Перекрестке 10 яблок
        );

        List<String> topThree = analyzer.getTopThreeSellingProducts(products, sales);

        assertEquals(3, topThree.size());
        assertEquals(tomatoes.getName(), topThree.get(0));
        assertEquals(pickle.getName(), topThree.get(1));
        assertEquals(apple.getName(), topThree.get(2));

    }




    @Test
    void getTopThreeSellingProducts_whenQuantityProductsSoldIsDifferent_thenReturn() {

        Product tomatoes = new Product(1, "Помидоры", "Овощи", 120.0);
        Product pickle = new Product(2, "Огурцы", "Овощи", 90.0);
        Product apple = new Product(3, "Яблоки", "Фрукты", 80.0);
        List<Product> products = Arrays.asList(
                tomatoes,
                pickle,
                apple,
                new Product(4, "Молоко", "Молочные продукты", 70.0)
        );

        List<Sale> sales = Arrays.asList(
                new Sale(1, 1, 1, 5, "2024-05-01"),  // В Пятерочке продано 5 помидоров
                new Sale(2, 1, 2, 10, "2024-05-01"),  // 3 огурца
                new Sale(3, 2, 1, 11, "2024-05-02"),  // В Магните 2 помидора
                new Sale(4, 3, 3, 9, "2024-05-03"),  // В Перекрестке 10 яблок
                new Sale(5, 3, 4, 6, "2024-05-03")  // В Перекрестке 10 яблок
        );

        List<String> topThree = analyzer.getTopThreeSellingProducts(products, sales);

        assertEquals(3, topThree.size());
        assertEquals(tomatoes.getName(), topThree.get(0));
        assertEquals(pickle.getName(), topThree.get(1));
        assertEquals(apple.getName(), topThree.get(2));

    }

    @Test
    void findStoreWithoutFruit_whenListEmpty_thenReturnNull() {

        List<Store> stores = analyzer.findStoreWithoutFruit(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        assertTrue(stores.isEmpty());

    }

    @Test
    void findStoreWithoutFruit_whenAllShopsHaveFruits_thenReturnNull() {

        List<Store> stores = Arrays.asList(
                new Store(1, "Пятерочка", "ул. Бассейная, 10", true),
                new Store(2, "Магнит", "ул. Ленина, 5", true),
                new Store(3, "Перекресток", "ул. Бассейная, 15", false),
                new Store(4, "Дикси", "ул. Гагарина, 20", true)
        );

        List<Product> products = Arrays.asList(
                new Product(1, "Помидоры", "Овощи", 120.0),
                new Product(2, "Огурцы", "Овощи", 90.0),
                new Product(3, "Яблоки", "Фрукты", 80.0),
                new Product(4, "Молоко", "Молочные продукты", 70.0),
                new Product(5, "Груши", "Фрукты", 90.0)

        );

        List<Warehouse> warehouses = Arrays.asList(
                new Warehouse(1, 3, 50),  // В магазине 1 (Пятерочка) 50 яблок
                new Warehouse(1, 5, 30),   // Груш 30
                new Warehouse(2, 5, 20),   // В Магните 20 груш
                new Warehouse(3, 3, 100),  // В Перекрестке 100 яблок
                new Warehouse(4, 3, 40)    // В Дикси 40 яблок
        );

        List<Store> storesWithoutFruit = analyzer.findStoreWithoutFruit(stores, products, warehouses);

        assertTrue(storesWithoutFruit.isEmpty());

    }

    @Test
    void findStoreWithoutFruit_whenThereIsOneStore_thenReturn() {

        Store store = new Store(4, "Дикси", "ул. Гагарина, 20", true);
        List<Store> stores = Arrays.asList(
                new Store(1, "Пятерочка", "ул. Бассейная, 10", true),
                new Store(2, "Магнит", "ул. Ленина, 5", true),
                new Store(3, "Перекресток", "ул. Бассейная, 15", false),
                store
        );

        List<Product> products = Arrays.asList(
                new Product(1, "Помидоры", "Овощи", 120.0),
                new Product(2, "Огурцы", "Овощи", 90.0),
                new Product(3, "Яблоки", "Фрукты", 80.0),
                new Product(4, "Молоко", "Молочные продукты", 70.0),
                new Product(5, "Груши", "Фрукты", 90.0)
        );

        List<Warehouse> warehouses = Arrays.asList(
                new Warehouse(1, 5, 50),  // В магазине 1 (Пятерочка) 50 груш
                new Warehouse(1, 3, 30),   // яблок 30
                new Warehouse(2, 3, 20),   // В Магните 20 яблок
                new Warehouse(3, 3, 100),  // В Перекрестке 100 яблок
                new Warehouse(4, 4, 40)    // В Дикси 40 молока
        );

        List<Store> storesWithoutFruit = analyzer.findStoreWithoutFruit(stores, products, warehouses);

        assertEquals(1, storesWithoutFruit.size());
        assertEquals(store.getId(), storesWithoutFruit.get(0).getId());

    }


    @Test
    void findStoreWithoutFruit_whenOneStoreHasFruitsAndOtherThings_thenReturnWithoutThisStore() {
        Store firstStore = new Store(2, "Магнит", "ул. Ленина, 5", true);
        Store secondStore = new Store(3, "Перекресток", "ул. Бассейная, 15", false);
        Store thirdStore = new Store(4, "Дикси", "ул. Гагарина, 20", true);
        Store fourthStore = new Store(1, "Пятерочка", "ул. Бассейная, 10", true);
        List<Store> stores = Arrays.asList(
                firstStore,
                secondStore,
                thirdStore,
                fourthStore
        );

        List<Product> products = Arrays.asList(
                new Product(1, "Помидоры", "Овощи", 120.0),
                new Product(2, "Огурцы", "Овощи", 90.0),
                new Product(3, "Яблоки", "Фрукты", 80.0),
                new Product(4, "Молоко", "Молочные продукты", 70.0),
                new Product(5, "Груши", "Фрукты", 90.0)
        );

        List<Warehouse> warehouses = Arrays.asList(
                new Warehouse(1, 1, 50),  // В магазине 1 (Пятерочка) 50 помидоров
                new Warehouse(1, 3, 30),   // Яблок 30
                new Warehouse(2, 1, 20),   // В Магните 20 помидоров
                new Warehouse(3, 2, 100),  // В Перекрестке 100 огурцов
                new Warehouse(4, 4, 40)    // В Дикси 40 молока
        );

        List<Store> storesWithoutFruit = analyzer.findStoreWithoutFruit(stores, products, warehouses);

        assertEquals(3, storesWithoutFruit.size());
        assertFalse(storesWithoutFruit.contains(fourthStore));

    }

    @Test
    void findStoreWithoutFruit_whenAllShopsDoNotHaveFruit_thenReturn() {

        Store firstStore = new Store(1, "Пятерочка", "ул. Бассейная, 10", true);
        Store secondStore = new Store(2, "Магнит", "ул. Ленина, 5", true);
        Store thirdStore = new Store(3, "Перекресток", "ул. Бассейная, 15", false);
        Store fourthStore = new Store(4, "Дикси", "ул. Гагарина, 20", true);
        List<Store> stores = Arrays.asList(
                firstStore,
                secondStore,
                thirdStore,
                fourthStore
        );

        List<Product> products = Arrays.asList(
                new Product(1, "Помидоры", "Овощи", 120.0),
                new Product(2, "Огурцы", "Овощи", 90.0),
                new Product(3, "Яблоки", "Фрукты", 80.0),
                new Product(4, "Молоко", "Молочные продукты", 70.0)
        );

        List<Warehouse> warehouses = Arrays.asList(
                new Warehouse(1, 1, 50),  // В магазине 1 (Пятерочка) 50 помидоров
                new Warehouse(1, 2, 30),   // Огурцов 30
                new Warehouse(2, 1, 20),   // В Магните 20 помидоров
                new Warehouse(3, 2, 100),  // В Перекрестке 100 огурцов
                new Warehouse(4, 4, 40)    // В Дикси 40 молока
        );

        List<Store> storesWithoutFruit = analyzer.findStoreWithoutFruit(stores, products, warehouses);

        assertEquals(4, storesWithoutFruit.size());
        assertTrue(storesWithoutFruit.containsAll(List.of(firstStore, secondStore, thirdStore, fourthStore)));

    }

    @Test
    void calculateRevenueEachStoreForMay_whenListEmpty_thenReturnNull(){

        Map<String, Double> revenueShops = analyzer.calculateRevenueEachStoreForMay(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        assertTrue(revenueShops.isEmpty());

    }

    @Test
    void calculateRevenueEachStoreForMay_whenStoresHaveSales_thenReturn() {

        Store firstStore = new Store(1, "Пятерочка", "ул. Бассейная, 10", true);
        List<Store> stores = Arrays.asList(
                firstStore,
                new Store(2, "Магнит", "ул. Ленина, 5", true),
                new Store(3, "Перекресток", "ул. Бассейная, 15", false),
                new Store(4, "Дикси", "ул. Гагарина, 20", true)
        );

        List<Product> products = Arrays.asList(
                new Product(1, "Помидоры", "Овощи", 120.0),
                new Product(2, "Огурцы", "Овощи", 90.0),
                new Product(3, "Яблоки", "Фрукты", 80.0),
                new Product(4, "Молоко", "Молочные продукты", 70.0)
        );

        List<Sale> sales = Arrays.asList(
                new Sale(1, 1, 1, 5, "2024-05-01"),  // В Пятерочке продано 5 помидоров
                new Sale(2, 1, 2, 3, "2024-05-01"),  // 3 огурца
                new Sale(3, 2, 1, 2, "2024-05-02"),  // В Магните 2 помидора
                new Sale(4, 3, 3, 10, "2024-05-03")  // В Перекрестке 10 яблок
        );

        Map<String, Double> revenueShops = analyzer.calculateRevenueEachStoreForMay(stores, products, sales);

        assertEquals(870, revenueShops.get(firstStore.getName()));

    }

    @Test
    void calculateRevenueEachStoreForMay_whenStoresNoHaveSalesForMay_thenReturn() {

        Store firstStore = new Store(1, "Пятерочка", "ул. Бассейная, 10", true);
        List<Store> stores = Arrays.asList(
                firstStore,
                new Store(2, "Магнит", "ул. Ленина, 5", true),
                new Store(3, "Перекресток", "ул. Бассейная, 15", false),
                new Store(4, "Дикси", "ул. Гагарина, 20", true)
        );

        List<Product> products = Arrays.asList(
                new Product(1, "Помидоры", "Овощи", 120.0),
                new Product(2, "Огурцы", "Овощи", 90.0),
                new Product(3, "Яблоки", "Фрукты", 80.0),
                new Product(4, "Молоко", "Молочные продукты", 70.0)
        );

        List<Sale> sales = Arrays.asList(
                new Sale(1, 1, 1, 5, "2024-02-01"),  // В Пятерочке продано 5 помидоров
                new Sale(2, 1, 2, 3, "2024-03-01"),  // 3 огурца
                new Sale(3, 2, 1, 2, "2025-05-02"),  // В Магните 2 помидора
                new Sale(4, 3, 3, 10, "2024-06-03")  // В Перекрестке 10 яблок
        );

        Map<String, Double> revenueShops = analyzer.calculateRevenueEachStoreForMay(stores, products, sales);

        assertTrue(revenueShops.isEmpty());

    }

}
