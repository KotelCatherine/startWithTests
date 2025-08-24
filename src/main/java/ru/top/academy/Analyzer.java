package ru.top.academy;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Analyzer {

    public List<Store> findOpenStoreOnBasseina(List<Store> stores) {
        return stores.stream()
                .filter(store -> store.isOpen() && (store.getAddress().contains("Бассейная")))
                .toList();
    }

    public int calculateTotalStockTomatoesInAllWarehouses(List<Warehouse> warehouses, int idProduct) {
        return warehouses.stream()
                .filter(warehouse -> warehouse.getProductId() == idProduct)
                .mapToInt(Warehouse::getQuantity)
                .sum();
    }

    public Map<String, Integer> findProductWhichLessFifty(List<Product> products, List<Warehouse> warehouses) {
        return warehouses.stream()
                .filter(warehouse -> warehouse.getQuantity() < 50)
                .collect(Collectors.toMap(
                        warehouse -> findNameProduct(products, warehouse.getProductId()),
                        Warehouse::getQuantity,
                        Math::min));
    }


    private String findNameProduct(List<Product> products, int productId) {
        return products.stream()
                .filter(product -> product.getId() == productId)
                .findFirst()
                .map(Product::getName)
                .orElse("null");
    }

    public List<String> getTopThreeSellingProducts(List<Product> products, List<Sale> sales) {

        Map<Integer, Integer> groupByIdProduct = sales.stream()
                .collect(Collectors.groupingBy(
                        Sale::getProductId,
                        Collectors.summingInt(Sale::getQuantity)));

        Map<Integer, String> productsIdAndName = products.stream()
                .collect(Collectors.toMap(
                        Product::getId,
                        Product::getName
                ));

        return groupByIdProduct.entrySet().stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
                .limit(3)
                .map(gp -> productsIdAndName.get(gp.getKey()))
                .toList();

    }

    public List<Store> findStoreWithoutFruit(List<Store> stores, List<Product> products, List<Warehouse> warehouses) {

        List<Integer> noFruits = products.stream()
                .filter(product -> !product.getCategory().equals("Фрукты"))
                .map(Product::getId)
                .toList();

        List<Integer> fruits = products.stream()
                .filter(product -> product.getCategory().equals("Фрукты"))
                .map(Product::getId)
                .toList();

        List<Warehouse> withoutFruits = warehouses.stream()
                .filter(warehouse -> noFruits.stream()
                        .anyMatch(product -> product == warehouse.getProductId()))
                .toList();

        List<Warehouse> withFruits = warehouses.stream()
                .filter(warehouse -> fruits.stream()
                        .anyMatch(product -> product == warehouse.getProductId()))
                .toList();

        return stores.stream()
                .filter(store -> withoutFruits.stream()
                        .anyMatch(warehouse -> warehouse.getStoreId() == store.getId() && withFruits.stream()
                                .noneMatch(warehouseWithFruits -> warehouseWithFruits.getStoreId() == store.getId())))
                .distinct()
                .toList();

    }

    public Map<String, Double> calculateRevenueEachStoreForMay(List<Store> stores, List<Product> products, List<Sale> sales) {

        return sales.stream()
                .filter(sale -> sale.getDate().getYear() == 2024 && sale.getDate().getMonthValue() == 5)
                .collect(Collectors.groupingBy(
                        sale -> getNameSuitableStore(stores, sale),
                        Collectors.summingDouble(sale -> calculateRevenue(products, sale))
                ));

    }

    private double calculateRevenue(List<Product> products, Sale sale) {
        return products.stream()
                .filter(product -> product.getId() == sale.getProductId())
                .findFirst()
                .map(product -> product.getPrice() * sale.getQuantity())
                .orElse(0.0);
    }

    private String getNameSuitableStore(List<Store> stores, Sale sale) {
        return stores.stream()
                .filter(store -> store.getId() == sale.getStoreId())
                .findFirst()
                .map(Store::getName)
                .orElse("null");
    }

    public List<String> findProductsThatAreInStockButHaveNeverBeenSold(List<Warehouse> warehouses, List<Sale> sales, List<Product> products) {

        List<Integer> productsId = sales.stream()
                .map(Sale::getProductId)
                .distinct()
                .toList();

        return warehouses.stream()
                .map(Warehouse::getProductId)
                .distinct()
                .filter(productId -> !productsId.contains(productId))
                .map(warehouseProductId -> products.stream()
                        .filter(product -> product.getId() == warehouseProductId)
                        .findFirst()
                        .map(Product::getName)
                        .orElse("null"))
                .toList();

    }

    public Map<String, Double> calculateAveragePriceProducts(List<Product> products) {
        return products.stream()
                .collect(Collectors.groupingBy(
                        Product::getCategory,
                        Collectors.averagingDouble(Product::getPrice)
                ));
    }

    public List<Store> findStoresWithNoSalesForMoreThanTwoDays(List<Sale> sales, List<Store> stores) {

        LocalDate today = LocalDate.of(2019, 5, 5);
        LocalDate currentDay = today.minusDays(2);

        List<Integer> suitableSale = sales.stream()
                .filter(sale -> sale.getDate().isBefore(currentDay) || sale.getDate().isEqual(currentDay))
                .map(Sale::getStoreId)
                .toList();

        return stores.stream()
                .filter(store -> suitableSale.contains(store.getId()))
                .toList();

    }
}
