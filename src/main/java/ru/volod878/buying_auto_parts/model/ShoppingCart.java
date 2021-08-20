package ru.volod878.buying_auto_parts.model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ShoppingCart {
    private StringProperty name;
    private final DoubleProperty price;
    private final IntegerProperty amount;
    private final DoubleProperty cost;

    private static final ObservableList<ShoppingCart> shoppingCart = FXCollections.observableArrayList();

    public ShoppingCart(String name, Double price, Integer amount, Double cost) {
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
        this.amount = new SimpleIntegerProperty(amount);
        this.cost = new SimpleDoubleProperty(cost);
    }

    public static void addAutoPart(AutoPartResult autoPartResult, Integer value) {
        shoppingCart.add(new ShoppingCart(
                autoPartResult.getName(),
                autoPartResult.getPrice(),
                value,
                autoPartResult.getPrice() * value
                )
        );
    }

    public static ObservableList<ShoppingCart> getShoppingCart() {
        return shoppingCart;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public double getPrice() {
        return price.get();
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public int getAmount() {
        return amount.get();
    }

    public IntegerProperty amountProperty() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount.set(amount);
    }

    public double getCost() {
        return cost.get();
    }

    public DoubleProperty costProperty() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost.set(cost);
    }
}
