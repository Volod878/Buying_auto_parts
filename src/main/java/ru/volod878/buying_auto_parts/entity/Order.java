package ru.volod878.buying_auto_parts.entity;

import ru.volod878.buying_auto_parts.model.OrderResult;
import ru.volod878.buying_auto_parts.model.ShoppingCartResult;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    @Column(name = "purchase_date")
    private LocalDateTime purchaseDate;

    @Column(name = "status")
    private String status;

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
        this.id = orderResult.getNumber();
        this.totalCost = orderResult.getTotalCost();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String data = orderResult.getPurchaseDate().format(dateTimeFormatter);
        this.purchaseDate = LocalDateTime.parse(data, dateTimeFormatter);
        this.status = orderResult.getStatus();

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

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
