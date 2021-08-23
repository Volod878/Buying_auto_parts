package ru.volod878.buying_auto_parts.entity;

import ru.volod878.buying_auto_parts.model.CustomerResult;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL,
    mappedBy = "customer",
    fetch = FetchType.EAGER)
    private List<Order> orders;

    public Customer() {
    }

    public Customer(CustomerResult customerResult) {
        this.id = customerResult.getId();
        this.name = customerResult.getName();
    }

    public void addOrder(Order order) {
        if (orders == null) orders = new ArrayList<>();
        orders.add(order);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
