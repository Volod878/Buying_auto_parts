package ru.volod878.buying_auto_parts.entity;

import ru.volod878.buying_auto_parts.model.AutoPartResult;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "auto_parts")
public class AutoPart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private double price;

    @Column(name = "in_stock")
    private int inStock;

    @Column(name = "delivery_period")
    private int deliveryPeriod;

    @ManyToMany(
            cascade = { CascadeType.ALL }
    )
    @JoinTable(
            name = "customer_auto_part",
            joinColumns = @JoinColumn(name = "auto_part_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id")
    )
    List<Customer> customers;

    public AutoPart() {
    }

    public AutoPart(String name, int price, int inStock, int deliveryPeriod) {
        this.name = name;
        this.price = price;
        this.inStock = inStock;
        this.deliveryPeriod = deliveryPeriod;
    }

    public void addCustomer(Customer customer) {
        if (customer == null) customers = new ArrayList<>();
        customers.add(customer);
    }

    public AutoPart(AutoPartResult autoPartResult) {
        this.id = autoPartResult.getVendorCode();
        this.name = autoPartResult.getName();
        this.price = autoPartResult.getPrice();
        this.inStock = autoPartResult.getInStock();
        this.deliveryPeriod = autoPartResult.getDeliveryPeriod();
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

    public void setPrice(int price) {
        this.price = price;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public int getDeliveryPeriod() {
        return deliveryPeriod;
    }

    public void setDeliveryPeriod(int deliveryPeriod) {
        this.deliveryPeriod = deliveryPeriod;
    }

    @Override
    public String toString() {
        return "AutoPart{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", inStock=" + inStock +
                ", deliveryPeriod=" + deliveryPeriod +
                '}';
    }
}
