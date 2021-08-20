package ru.volod878.buying_auto_parts.model;

import javafx.beans.property.*;
import ru.volod878.buying_auto_parts.entity.AutoPart;

/**
 * Класс-модель для автозапчастей.
 */

public class AutoPartResult {

    private final IntegerProperty vendorCode;
    private final StringProperty name;
    private final DoubleProperty price;
    private final IntegerProperty inStock;
    private final IntegerProperty deliveryPeriod;

    /**
     * Конструктор по умолчанию.
     */
    public AutoPartResult() {
        this.name = new SimpleStringProperty(null);
        this.price = new SimpleDoubleProperty(0.0);

        // Какие-то фиктивные начальные данные для удобства тестирования.
        this.inStock = new SimpleIntegerProperty(0);
        this.vendorCode = new SimpleIntegerProperty(0);
        this.deliveryPeriod = new SimpleIntegerProperty(0);
    }

    public AutoPartResult(AutoPart autoPart) {
        this.name = new SimpleStringProperty(autoPart.getName());
        this.price = new SimpleDoubleProperty(autoPart.getPrice());

        this.inStock = new SimpleIntegerProperty(autoPart.getInStock());
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