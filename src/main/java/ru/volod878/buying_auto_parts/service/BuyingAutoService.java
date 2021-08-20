package ru.volod878.buying_auto_parts.service;

import javafx.collections.ObservableList;

public interface BuyingAutoService<Entity> {

    ObservableList<Entity> getAllEntityResults();

    void saveEntityResult(Entity entityResult);

    Entity getEntityResult(int id);

    void deleteEntityResult(int id);

}
