package ru.volod878.buying_auto_parts.service;

import javafx.collections.ObservableList;

/**
 * Интерфейс-сервис для адаптации классов-сущностей и классов-моделей.
 * @param <Model> объектов с которыми работают контроллеры
 */
public interface BuyingAutoService<Model> {
    /**
     * Получить все объекты модели из БД
     * @return наблюдаемый список моделей
     */
    ObservableList<Model> getAllEntityResults();

    /**
     * Сохранить/обновить объект модели в БД
     * @param model модель готовая к сохранению
     */
    void saveEntityResult(Model model);

    /**
     * Получить один элемент из БД
     * @param id объекта которых нужно получить из БД
     * @return модель по его id
     */
    Model getEntityResult(int id);

    /**
     * Удалить выбранный элемент из БД
     * @param id модели, которая будет удалена
     */
    void deleteEntityResult(int id);

}
