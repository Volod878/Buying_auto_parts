package ru.volod878.buying_auto_parts.model;

import javafx.beans.property.*;
import ru.volod878.buying_auto_parts.entity.Shop;

/**
 * Класс-модель для автозапчастей в магазине.
 */

public class ShopResult {

    private final IntegerProperty vendorCode;
    private final StringProperty name;
    private final DoubleProperty price;
    private final IntegerProperty inStock;
    private final IntegerProperty deliveryPeriod;

    public ShopResult(Shop shop) {
        this.name = new SimpleStringProperty(shop.getAutoPart().getName());
        this.price = new SimpleDoubleProperty(shop.getAutoPart().getPrice());
        this.inStock = new SimpleIntegerProperty(shop.getInStock());
        this.vendorCode = new SimpleIntegerProperty(shop.getId());
        this.deliveryPeriod = new SimpleIntegerProperty(shop.getAutoPart().getDeliveryPeriod());
    }

    public int getVendorCode() {
        return vendorCode.get();
    }

    public IntegerProperty vendorCodeProperty() {
        return vendorCode;
    }

    public void setVendorCode(int vendorCode) {
        this.vendorCode.set(vendorCode);
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

    public int getInStock() {
        return inStock.get();
    }

    public IntegerProperty inStockProperty() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock.set(inStock);
    }

    public int getDeliveryPeriod() {
        return deliveryPeriod.get();
    }

    public IntegerProperty deliveryPeriodProperty() {
        return deliveryPeriod;
    }

    public void setDeliveryPeriod(int deliveryPeriod) {
        this.deliveryPeriod.set(deliveryPeriod);
    }
}