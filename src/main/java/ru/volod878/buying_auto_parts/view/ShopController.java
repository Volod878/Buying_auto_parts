package ru.volod878.buying_auto_parts.view;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.stage.Stage;
import ru.volod878.buying_auto_parts.MainApp;
import ru.volod878.buying_auto_parts.model.AutoPartResult;
import ru.volod878.buying_auto_parts.model.ShoppingCart;
import ru.volod878.buying_auto_parts.service.AutoPartService;
import ru.volod878.buying_auto_parts.service.BuyingAutoService;

public class ShopController {

    @FXML
    private TableView<AutoPartResult> autoPartTable;
    @FXML
    private TableColumn<AutoPartResult, String> nameColumn;
    @FXML
    private TableColumn<AutoPartResult, Double> priceColumn;
    @FXML
    private TableColumn<AutoPartResult, Integer> inStockColumn;
    @FXML
    private TableColumn<AutoPartResult, Integer> deliveryPeriodColumn;

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

    private static final BuyingAutoService<AutoPartResult>
            BUYING_AUTO_SERVICE = new AutoPartService(MainApp.getFactory());

    public ShopController() {
    }

    /**
     * Инициализация класса-контроллера. Этот метод вызывается автоматически
     * после того, как fxml-файл будет загружен.
     */
    @FXML
    private void initialize() {
        // Инициализация таблицы автозапчастей с четырьмя столбцами.
        //todo запчасти которые в магазине
        ObservableList<AutoPartResult> autoPartResults = BUYING_AUTO_SERVICE.getAllEntityResults();
        autoPartTable.setItems(autoPartResults);

        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        inStockColumn.setCellValueFactory(cellData -> cellData.getValue().inStockProperty().asObject());
        deliveryPeriodColumn.setCellValueFactory(cellData
                -> cellData.getValue().deliveryPeriodProperty().asObject());

        // Очистка дополнительной информации
        showAutoPartDetails(null);

        // Слушаем изменения выбора, и при изменении отображаем
        // дополнительную информацию об автозапчасти.
        autoPartTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showAutoPartDetails(newValue));
    }

    /**
     * Заполняет все текстовые поля, отображая подробности об автозапчасти.
     * Если указанная автозапчасть = null, то все текстовые поля очищаются
     */
    private void showAutoPartDetails(AutoPartResult autoPartResult) {
        if (autoPartResult != null) {
            // Заполняем метки информацией из объекта AutoPart.
            nameLabel.setText(autoPartResult.getName());
            priceLabel.setText(Double.toString(autoPartResult.getPrice()));
            inStockLabel.setText(Integer.toString(autoPartResult.getInStock()));
            deliveryPeriodLabel.setText(Integer.toString(autoPartResult.getDeliveryPeriod()));
            vendorCodeLabel.setText(Integer.toString(autoPartResult.getVendorCode()));
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
        AutoPartResult selectedAutoPartResult = autoPartTable.getSelectionModel().getSelectedItem();
        int number = spinner.getValue();
        if (selectedAutoPartResult != null && number != 0) {
            ShoppingCart.addAutoPart(selectedAutoPartResult, number);
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
