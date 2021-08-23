package ru.volod878.buying_auto_parts.model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.volod878.buying_auto_parts.entity.Order;
import ru.volod878.buying_auto_parts.entity.ShoppingCart;

import java.util.List;

public class OrderResult {
    private final IntegerProperty number;
    private final DoubleProperty totalCost;
    private final StringProperty data;
    private final StringProperty status;

    private final ObservableList<ShoppingCartResult> allPurchases = FXCollections.observableArrayList();

    private CustomerResult customerResult;

    public OrderResult() {
        this.number = new SimpleIntegerProperty();
        this.totalCost = new SimpleDoubleProperty();
        this.data = new SimpleStringProperty();
        this.status = new SimpleStringProperty();
    }

    public OrderResult(Order order) {
        this.number = new SimpleIntegerProperty(order.getId());
        this.totalCost = new SimpleDoubleProperty(order.getTotalCost());
        this.data = new SimpleStringProperty();
        this.status = new SimpleStringProperty();
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

    public String getData() {
        return data.get();
    }

    public StringProperty dataProperty() {
        return data;
    }

    public void setData(String data) {
        this.data.set(data);
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
        this.allPurchases.add(new ShoppingCartResult(
                        shopResult.getName(),
                        shopResult.getPrice(),
                        amount,
                        shopResult.getPrice() * amount
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
