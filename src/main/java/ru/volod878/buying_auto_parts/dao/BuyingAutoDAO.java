package ru.volod878.buying_auto_parts.dao;

import java.util.List;

public interface BuyingAutoDAO<Entity> {
    List<Entity> getAllEntities();

    void saveEntity(Entity entity);

    Entity getEntity(int id);

    void deleteEntity(int id);
}
