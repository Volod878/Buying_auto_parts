package ru.volod878.buying_auto_parts.model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.volod878.buying_auto_parts.entity.Order;
import ru.volod878.buying_auto_parts.entity.ShoppingCart;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class OrderResult {
    private final IntegerProperty number;
    private final DoubleProperty totalCost;
    private final ObjectProperty<LocalDateTime> purchaseDate;
    private final StringProperty status;

    private final ObservableList<ShoppingCartResult> allPurchases = FXCollections.observableArrayList();

    private CustomerResult customerResult;

    public OrderResult() {
        this.number = new SimpleIntegerProperty();
        this.totalCost = new SimpleDoubleProperty();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String data = LocalDateTime.now().format(dateTimeFormatter);
        this.purchaseDate = new SimpleObjectProperty<>(LocalDateTime.parse(data, dateTimeFormatter));
        this.status = new SimpleStringProperty();
    }

    public OrderResult(Order order) {
        this.number = new SimpleIntegerProperty(order.getId());
        this.totalCost = new SimpleDoubleProperty(order.getTotalCost());
        this.purchaseDate = new SimpleObjectProperty<>(order.getPurchaseDate());
        this.status = new SimpleStringProperty(order.getStatus());
    }

    public int getNumber() {
        return number.get();
    }

    public IntegerProperty numberProperty() {
        return number;
    }

    public void setNumber(int number) {
        this.number.set(number);
    }

    public double getTotalCost() {
        return totalCost.get();
    }

    public DoubleProperty totalCostProperty() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost.set(totalCost);
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate.get();
    }

    public ObjectProperty<LocalDateTime> purchaseDateProperty() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate.set(purchaseDate);
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public ObservableList<ShoppingCartResult> getAllPurchases() {
        return allPurchases;
    }

    public void setAllPurchases(List<ShoppingCart> allPurchases) {
        for (ShoppingCart shoppingCart: allPurchases)
            this.allPurchases.add(new ShoppingCartResult(shoppingCart));
    }

    public void addPurchases(ShopResult shopResult, Integer amount) {
        BigDecimal total = BigDecimal.valueOf(shopResult.getPrice()).multiply(new BigDecimal(amount));
        this.allPurchases.add(new ShoppingCartResult(
                        shopResult.getName(),
                        shopResult.getPrice(),
                        amount,
                        total.doubleValue()
                )
        );
    }

    public CustomerResult getCustomerResult() {
        return customerResult;
    }

    public void setCustomerResult(CustomerResult customerResult) {
        this.customerResult = customerResult;
    }
}
