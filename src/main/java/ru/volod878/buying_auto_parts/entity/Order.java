package ru.volod878.buying_auto_parts.entity;

import ru.volod878.buying_auto_parts.model.OrderResult;
import ru.volod878.buying_auto_parts.model.ShoppingCartResult;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "total_cost")
    private double totalCost;

    @OneToMany(cascade = {
            CascadeType.ALL
    },
            mappedBy = "order",
            fetch = FetchType.EAGER)
    private List<ShoppingCart> shoppingCart;

    @ManyToOne(cascade = {
            CascadeType.ALL
    },
            fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Order() {
    }

    public Order(OrderResult orderResult) {
        this.totalCost = orderResult.getTotalCost();

        this.shoppingCart = new ArrayList<>();
        for (ShoppingCartResult shoppingCartResult: orderResult.getAllPurchases())
            this.shoppingCart.add(new ShoppingCart(shoppingCartResult, this));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public List<ShoppingCart> getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(List<ShoppingCart> shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
