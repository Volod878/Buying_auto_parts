package ru.volod878.buying_auto_parts.entity;

import ru.volod878.buying_auto_parts.model.ShopResult;

import javax.persistence.*;

@Entity
@Table(name = "shop")
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "in_stock")
    private int inStock;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "auto_part_id")
    private AutoPart autoPart;

    public Shop() {
    }

    public Shop(int inStock) {
        this.inStock = inStock;
    }

    public Shop(ShopResult shopResult) {
        this.id = shopResult.getVendorCode();
        this.inStock = shopResult.getInStock();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public AutoPart getAutoPart() {
        return autoPart;
    }

    public void setAutoPart(AutoPart autoPart) {
        this.autoPart = autoPart;
    }
}
