package ru.volod878.buying_auto_parts.view;

import javafx.fxml.FXML;
import ru.volod878.buying_auto_parts.MainApp;
import ru.volod878.buying_auto_parts.model.CustomerResult;

/**
 * Главное окно с лентой меню
 */
public class RootLayoutController {

    private MainApp mainApp;

    /**
     * Вызывается главным приложением, которое даёт на себя ссылку
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Вызывается, когда пользователь нажимает кнопку "Список клиентов"
     * Открывает таблица с информацией о клиентах
     */
    @FXML
    public void handleCustomerList() {
        mainApp.showCustomer();
    }

    /**
     * Вызывается, когда пользователь нажимает кнопку "Склад"
     * Открывает таблица с авто запчастями на складе
     */
    @FXML
    private void handleAutoPartsWarehouse() {
        mainApp.showWareHouse();
    }

    /**
     * Вызывается, когда пользователь нажимает кнопку "Магазин"
     * Открывает таблица с авто запчастями в магазине
     */
    @FXML
    public void handleAutoPartsShop() {
        mainApp.showShop(new CustomerResult());
    }

    /**
     * Вызывается, когда пользователь нажимает кнопку "Справка"
     * Открывает информация о приложении
     */
    @FXML
    public void handleHelp() {
        mainApp.showHelp();
    }
}
