package ru.volod878.buying_auto_parts.entity;

import ru.volod878.buying_auto_parts.model.AutoPartResult;

import javax.persistence.*;

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

    @Column(name = "amount")
    private int amount;

    @Column(name = "delivery_period")
    private int deliveryPeriod;

    public AutoPart() {
    }

    public AutoPart(String name, int price, int amount, int deliveryPeriod) {
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.deliveryPeriod = deliveryPeriod;
    }

    public AutoPart(AutoPartResult autoPartResult) {
        this.id = autoPartResult.getVendorCode();
        this.name = autoPartResult.getName();
        this.price = autoPartResult.getPrice();
        this.amount = autoPartResult.getInStock();
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
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
                ", amount=" + amount +
                ", deliveryPeriod=" + deliveryPeriod +
                '}';
    }
}
