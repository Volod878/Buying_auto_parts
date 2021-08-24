package ru.volod878.buying_auto_parts.dao;

import java.util.List;

public interface BuyingAutoDAO<Entity> {
    /**
     * Получить все элементы Entity из БД
     * @return список объектов Entity
     */
    List<Entity> getAllEntities();

    /**
     * Сохранить/обновить объект в БД
     * @param entity сущность готовая к сохранению
     */
    void saveEntity(Entity entity);

    /**
     * Получить один элемент из БД
     * @param id объекта которых нужно получить из БД
     * @return сущность по его id
     */
    Entity getEntity(int id);

    /**
     * Удалить выбранный элемент из БД
     * @param id объекта, который будет удален
     */
    void deleteEntity(int id);
}
