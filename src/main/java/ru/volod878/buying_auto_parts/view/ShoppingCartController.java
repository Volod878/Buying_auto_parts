package ru.volod878.buying_auto_parts.view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import ru.volod878.buying_auto_parts.MainApp;
import ru.volod878.buying_auto_parts.model.OrderResult;
import ru.volod878.buying_auto_parts.model.ShoppingCartResult;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Окно-корзина для оплаты заказа
 */
public class ShoppingCartController {

    @FXML
    public TableView<ShoppingCartResult> shoppingCartTable;
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
    private boolean okClicked = false;

    public void initTable() {
        // Инициализация таблицы автозапчастей в корзине
        ObservableList<ShoppingCartResult> shoppingCartResult = orderResult.getAllPurchases();
        shoppingCartTable.setItems(shoppingCartResult);

        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        amountColumn.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());
        costColumn.setCellValueFactory(cellData -> cellData.getValue().costProperty().asObject());


        // Подсчет общей стоимость заказа
        List<Double> total = shoppingCartResult.stream()
                .map(ShoppingCartResult::getCost)
                .collect(Collectors.toList());
        double costTotal = total.stream().mapToDouble(e -> e).sum();
        totalCostLabel.setText(String.format("%.2f", costTotal));
    }

    /**
     * Устанавливает сцену для этого окна.
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Устанавливаем заказ, который нужно оплатить
     */
    public void setOrder(OrderResult orderResult) {
        this.orderResult = orderResult;
    }

    /**
     * Устанавливаем имя клиента
     */
    public void setCustomerLabel() {
        customerLabel.setText(orderResult.getCustomerResult().getName());
    }

    /**
     * @return true, если пользователь кликнул Оплатить, в другом случае false.
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Вызывается, когда пользователь кликнул по кнопке Оплатить.
     */
    @FXML
    private void handlePay() {
        // Если корзина не пустая, оплачиваем заказ
        double totalCost = Double.parseDouble(totalCostLabel.getText().replace(",", "."));
        if (totalCost != 0.0) {
            orderResult.setTotalCost(totalCost);

            okClicked = true;
            dialogStage.close();

    } else {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(MainApp.getPrimaryStage());
        alert.setTitle("Внимание!");
        alert.setHeaderText("Козина пустая.");
        alert.setContentText("Пожалуйста, добавьте хотя бы один товар в корзину.");

        alert.showAndWait();
    }
    }

    /**
     * Вызывается, когда пользователь кликнул по кнопке Назад.
     */
    @FXML
    private void handleBack() {
        dialogStage.close();
    }
}
