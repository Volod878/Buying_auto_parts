package ru.volod878.buying_auto_parts.model;

import javafx.beans.property.*;
import ru.volod878.buying_auto_parts.entity.AutoPart;

/**
 * Класс-модель для автозапчастей.
 * С данным классом работает контроллер
 */
public class AutoPartResult {

    private final IntegerProperty vendorCode;
    private final StringProperty name;
    private final DoubleProperty price;
    private final IntegerProperty amount;
    private final IntegerProperty deliveryPeriod;

    public AutoPartResult() {
        this.name = new SimpleStringProperty();
        this.price = new SimpleDoubleProperty();
        this.amount = new SimpleIntegerProperty();
        this.vendorCode = new SimpleIntegerProperty();
        this.deliveryPeriod = new SimpleIntegerProperty();
    }

    public AutoPartResult(AutoPart autoPart) {
        this.name = new SimpleStringProperty(autoPart.getName());
        this.price = new SimpleDoubleProperty(autoPart.getPrice());
        this.amount = new SimpleIntegerProperty(autoPart.getAmount());
        this.vendorCode = new SimpleIntegerProperty(autoPart.getId());
        this.deliveryPeriod = new SimpleIntegerProperty(autoPart.getDeliveryPeriod());
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

    public int getAmount() {
        return amount.get();
    }

    public IntegerProperty amountProperty() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount.set(amount);
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