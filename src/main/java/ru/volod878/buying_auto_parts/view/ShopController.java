package ru.volod878.buying_auto_parts.view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.stage.Stage;
import ru.volod878.buying_auto_parts.MainApp;
import ru.volod878.buying_auto_parts.model.ShopResult;
import ru.volod878.buying_auto_parts.model.ShoppingCart;
import ru.volod878.buying_auto_parts.service.BuyingAutoService;
import ru.volod878.buying_auto_parts.service.ShopService;

public class ShopController {

    @FXML
    private TableView<ShopResult> autoPartOfShopTable;
    @FXML
    private TableColumn<ShopResult, String> nameColumn;
    @FXML
    private TableColumn<ShopResult, Double> priceColumn;
    @FXML
    private TableColumn<ShopResult, Integer> inStockColumn;
    @FXML
    private TableColumn<ShopResult, Integer> deliveryPeriodColumn;

    @FXML
    private Label vendorCodeLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label inStockLabel;
    @FXML
    private Label deliveryPeriodLabel;
    @FXML
    private Spinner<Integer> spinner;

    private Stage dialogStage;
    private boolean okClicked = false;

    private static final BuyingAutoService<ShopResult>
            BUYING_AUTO_SERVICE = new ShopService(MainApp.getFactory());

    public ShopController() {
    }

    @FXML
    private void initialize() {
        // Инициализация таблицы автозапчастей с четырьмя столбцами.
        ObservableList<ShopResult> shopResults = BUYING_AUTO_SERVICE.getAllEntityResults();
        autoPartOfShopTable.setItems(shopResults);

        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        inStockColumn.setCellValueFactory(cellData -> cellData.getValue().inStockProperty().asObject());
        deliveryPeriodColumn.setCellValueFactory(cellData
                -> cellData.getValue().deliveryPeriodProperty().asObject());

        // Очистка дополнительной информации
        showShopDetails(null);

        // Слушаем изменения выбора, и при изменении отображаем
        // дополнительную информацию об автозапчасти.
        autoPartOfShopTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showShopDetails(newValue));
    }

    /**
     * Заполняет все текстовые поля, отображая подробности об автозапчасти.
     * Если указанная автозапчасть = null, то все текстовые поля очищаются
     */
    private void showShopDetails(ShopResult shopResult) {
        if (shopResult != null) {
            // Заполняем метки информацией из объекта AutoPart.
            nameLabel.setText(shopResult.getName());
            priceLabel.setText(Double.toString(shopResult.getPrice()));
            inStockLabel.setText(Integer.toString(shopResult.getInStock()));
            deliveryPeriodLabel.setText(Integer.toString(shopResult.getDeliveryPeriod()));
            vendorCodeLabel.setText(Integer.toString(shopResult.getVendorCode()));
        } else {
            // Если AutoPart = null, то убираем весь текст.
            nameLabel.setText("");
            priceLabel.setText("");
            inStockLabel.setText("");
            deliveryPeriodLabel.setText("");
            vendorCodeLabel.setText("");
        }
    }

    /**
     * Вызывается, когда пользователь нажимает кнопку "В корзину"
     * Открывает диалоговое окно для изменения выбранной автозапчасти.
     */
    @FXML
    public void handleAddAutoPart() {
        ShopResult selectedShopResult = autoPartOfShopTable.getSelectionModel().getSelectedItem();
        int number = spinner.getValue();
        if (selectedShopResult != null && number != 0) {
            ShoppingCart.addAutoPartInShop(selectedShopResult, number);
            initialize();
        } else {
            // Ничего не выбрано.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(MainApp.getPrimaryStage());
            alert.setTitle("Внимание!");
            alert.setHeaderText("Никто не выбран");
            alert.setContentText("Пожалуйста, выберите автозапчасть и укажите количество.");

            alert.showAndWait();
        }
    }

    /**
     * Вызывается, когда пользователь нажимает кнопку "Корзина"
     */
    @FXML
    public void handleShoppingCart() {
        boolean okClicked = MainApp.showShoppingCardDialog();
        if (okClicked) {
            //todo оформить клиенту заказ
            initialize();
        }
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

}
