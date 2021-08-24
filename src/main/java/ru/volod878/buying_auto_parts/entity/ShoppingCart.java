package ru.volod878.buying_auto_parts.entity;

import ru.volod878.buying_auto_parts.model.ShoppingCartResult;

import javax.persistence.*;

/**
 * Класс-entity связывающий объекты ShoppingCart с таблицей в БД
 */
@Entity
@Table(name = "shopping_cart")
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private double price;

    @Column(name = "amount")
    private int amount;

    @Column(name = "cost")
    private double cost;

    @ManyToOne(cascade = {
            CascadeType.PERSIST,
            CascadeType.REFRESH,
            CascadeType.MERGE,
            CascadeType.DETACH
    },
            fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    public ShoppingCart() {
    }

    public ShoppingCart(ShoppingCartResult shoppingCartResult, Order order) {
        this(shoppingCartResult);
        this.order = order;
    }

    public ShoppingCart(ShoppingCartResult shoppingCartResult) {
        this.name = shoppingCartResult.getName();
        this.price = shoppingCartResult.getPrice();
        this.amount = shoppingCartResult.getAmount();
        this.cost = shoppingCartResult.getCost();
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
