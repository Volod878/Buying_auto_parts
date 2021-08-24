package ru.volod878.buying_auto_parts.model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.volod878.buying_auto_parts.entity.Customer;
import ru.volod878.buying_auto_parts.entity.Order;

public class CustomerResult {
    private final IntegerProperty id;
    private final StringProperty name;
    private final IntegerProperty numberOfOrders;

    private final ObservableList<OrderResult> allOrders;

    public CustomerResult() {
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.numberOfOrders = new SimpleIntegerProperty();

        this.allOrders = FXCollections.observableArrayList();
    }

    public CustomerResult(Customer customer) {
        this.id = new SimpleIntegerProperty(customer.getId());
        this.name = new SimpleStringProperty(customer.getName());
        this.numberOfOrders = new SimpleIntegerProperty(customer.getOrders().size());

        this.allOrders = FXCollections.observableArrayList();
        for (Order order: customer.getOrders())
            this.allOrders.add(
                    new OrderResult(order));
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
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

    public int getNumberOfOrders() {
        return numberOfOrders.get();
    }

    public IntegerProperty numberOfOrdersProperty() {
        return numberOfOrders;
    }

    public void setNumberOfOrders(int numberOfOrders) {
        this.numberOfOrders.set(numberOfOrders);
    }

    public void addOrder(OrderResult orderResult) {
        this.allOrders.add(orderResult);
    }

    public ObservableList<OrderResult> getAllOrders() {
        return allOrders;
    }
}
