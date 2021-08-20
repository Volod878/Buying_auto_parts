package ru.volod878.buying_auto_parts.view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import ru.volod878.buying_auto_parts.model.AutoPartResult;
import ru.volod878.buying_auto_parts.model.ShoppingCart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ShoppingCartController {

    @FXML
    public TableView<ShoppingCart> shoppingCartTable;
    @FXML
    public TableColumn<ShoppingCart, String> nameColumn;
    @FXML
    public TableColumn<ShoppingCart, Double> priceColumn;
    @FXML
    public TableColumn<ShoppingCart, Integer> amountColumn;
    @FXML
    public TableColumn<ShoppingCart, Double> costColumn;
    @FXML
    public Label totalCostLabel;
    @FXML
    public Label customerLabel;

    private Stage dialogStage;
    private boolean okClicked = false;

    public ShoppingCartController() {
    }


    /**
     * Инициализация класса-контроллера. Этот метод вызывается автоматически
     * после того, как fxml-файл будет загружен.
     */
    @FXML
    private void initialize() {
        // Инициализация таблицы автозапчастей с четырьмя столбцами.
        ObservableList<ShoppingCart> shoppingCart = ShoppingCart.getShoppingCart();
        shoppingCartTable.setItems(shoppingCart);

        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        amountColumn.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());
        costColumn.setCellValueFactory(cellData -> cellData.getValue().costProperty().asObject());

        List<Double> total = shoppingCart.stream()
                .map(ShoppingCart::getCost)
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
     * @return true, если пользователь кликнул Ок, в другом случае false.
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Вызывается, когда пользователь кликнул по кнопке Ок.
     */
    @FXML
    private void handlePay() {
        okClicked = true;
        dialogStage.close();
    }

    /**
     * Вызывается, когда пользователь кликнул по кнопке Отмена.
     */
    @FXML
    private void handleBack() {
        dialogStage.close();
    }
}
