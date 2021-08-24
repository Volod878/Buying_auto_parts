package ru.volod878.buying_auto_parts.view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import ru.volod878.buying_auto_parts.MainApp;
import ru.volod878.buying_auto_parts.model.OrderResult;
import ru.volod878.buying_auto_parts.model.ShoppingCartResult;
import ru.volod878.buying_auto_parts.service.BuyingAutoService;
import ru.volod878.buying_auto_parts.service.OrderService;

/**
 * Окно для просмотра списка товаров выбранного заказа клиента.
 */
public class PurchaseDetailsController {


    @FXML
    public TableView<ShoppingCartResult> purchaseDetailsTable;
    @FXML
    public TableColumn<ShoppingCartResult, String> nameColumn;
    @FXML
    public TableColumn<ShoppingCartResult, Double> priceColumn;
    @FXML
    public TableColumn<ShoppingCartResult, Integer> amountColumn;
    @FXML
    public TableColumn<ShoppingCartResult, Double> costColumn;
    @FXML
    public Label totalCostLabel;
    @FXML
    public Label customerLabel;

    private Stage dialogStage;
    private OrderResult orderResult;

    public static final BuyingAutoService<OrderResult>
            SHOPPING_CART_SERVICE = new OrderService(MainApp.getFactory());

    /**
     * Инициализация таблицы со списком покупок
     */
    public void initTable() {
        // Берем информацию о заказе из БД и отображаем ее в таблице
        ObservableList<ShoppingCartResult> shoppingCartResult =
                SHOPPING_CART_SERVICE.getEntityResult(orderResult.getNumber())
                        .getAllPurchases();
        purchaseDetailsTable.setItems(shoppingCartResult);

        totalCostLabel.setText(String.format("%.2f", orderResult.getTotalCost()));
        customerLabel.setText(orderResult.getCustomerResult().getName());

        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        amountColumn.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());
        costColumn.setCellValueFactory(cellData -> cellData.getValue().costProperty().asObject());

    }

    /**
     * Устанавливает сцену для этого окна.
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Устанавливаем заказ для просмотра
     */
    public void setOrder(OrderResult orderResult) {
        this.orderResult = orderResult;
    }

    /**
     * Вызывается, когда пользователь кликнул по кнопке Назад.
     * Закрывается диалоговое окно
     */
    @FXML
    private void handleOk() {
        dialogStage.close();
    }
}
