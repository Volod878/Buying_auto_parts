package ru.volod878.buying_auto_parts.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.hibernate.SessionFactory;

import ru.volod878.buying_auto_parts.dao.BuyingAutoDAO;
import ru.volod878.buying_auto_parts.dao.AutoPartDAO;
import ru.volod878.buying_auto_parts.entity.AutoPart;
import ru.volod878.buying_auto_parts.model.AutoPartResult;

import java.util.List;

/**
 * Класс отвечает за работу с сущностями AutoPart и моделями AutoPartResult.
 * Данный класс адаптирует эти объекты между собой
 */
public class AutoPartService implements BuyingAutoService<AutoPartResult> {

    private final BuyingAutoDAO<AutoPart> buyingAutoDAO;

    public AutoPartService(SessionFactory factory) {
        this.buyingAutoDAO = new AutoPartDAO(factory);
    }

    @Override
    public ObservableList<AutoPartResult> getAllEntityResults() {
        List<AutoPart> allAutoParts = buyingAutoDAO.getAllEntities();
        ObservableList<AutoPartResult> autoPartResults = FXCollections.observableArrayList();
        for (AutoPart autoPart: allAutoParts) autoPartResults.add(new AutoPartResult(autoPart));
        return autoPartResults;
    }

    @Override
    public void saveEntityResult(AutoPartResult autoPartResult) {
        AutoPart autoPart = new AutoPart(autoPartResult);
        buyingAutoDAO.saveEntity(autoPart);
    }

    @Override
    public AutoPartResult getEntityResult(int id) {
        return new AutoPartResult(buyingAutoDAO.getEntity(id));
    }

    @Override
    public void deleteEntityResult(int id) {
        buyingAutoDAO.deleteEntity(id);
    }
}
