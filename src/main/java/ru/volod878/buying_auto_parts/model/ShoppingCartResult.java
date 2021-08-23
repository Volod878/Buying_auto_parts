package ru.volod878.buying_auto_parts.model;

import javafx.beans.property.*;
import ru.volod878.buying_auto_parts.entity.ShoppingCart;

public class ShoppingCartResult {
    private final StringProperty name;
    private final DoubleProperty price;
    private final IntegerProperty amount;
    private final DoubleProperty cost;

    public ShoppingCartResult(String name, Double price, Integer amount, Double cost) {
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
        this.amount = new SimpleIntegerProperty(amount);
        this.cost = new SimpleDoubleProperty(cost);
    }

    public ShoppingCartResult(ShoppingCart shoppingCart) {
        this.name = new SimpleStringProperty(shoppingCart.getName());
        this.price = new SimpleDoubleProperty(shoppingCart.getPrice());
        this.amount = new SimpleIntegerProperty(shoppingCart.getAmount());
        this.cost = new SimpleDoubleProperty(shoppingCart.getCost());
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
