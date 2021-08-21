package ru.volod878.buying_auto_parts.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.SessionFactory;
import ru.volod878.buying_auto_parts.dao.BuyingAutoDAO;
import ru.volod878.buying_auto_parts.dao.ShopDAO;
import ru.volod878.buying_auto_parts.entity.Shop;
import ru.volod878.buying_auto_parts.model.ShopResult;

import java.util.List;

public class ShopService implements BuyingAutoService<ShopResult> {

    private final BuyingAutoDAO<Shop> buyingAutoDAO;

    public ShopService(SessionFactory factory) {
        this.buyingAutoDAO = new ShopDAO(factory);
    }

    @Override
    public ObservableList<ShopResult> getAllEntityResults() {
        List<Shop> allAutoPartsOfShop = buyingAutoDAO.getAllEntities();
        ObservableList<ShopResult> autoPartResults = FXCollections.observableArrayList();
        for (Shop shop: allAutoPartsOfShop) autoPartResults.add(new ShopResult(shop));
        return autoPartResults;
    }

    @Override
    public void saveEntityResult(ShopResult shopResult) {
        Shop autoPartOfShop = new Shop(shopResult);
        buyingAutoDAO.saveEntity(autoPartOfShop);
    }

    @Override
    public ShopResult getEntityResult(int id) {
        return new ShopResult(buyingAutoDAO.getEntity(id));
    }

    @Override
    public void deleteEntityResult(int id) {
        buyingAutoDAO.deleteEntity(id);
    }
}
