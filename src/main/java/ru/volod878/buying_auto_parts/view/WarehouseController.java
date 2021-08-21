package ru.volod878.buying_auto_parts.view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ru.volod878.buying_auto_parts.MainApp;
import ru.volod878.buying_auto_parts.model.AutoPartResult;
import ru.volod878.buying_auto_parts.service.AutoPartService;
import ru.volod878.buying_auto_parts.service.BuyingAutoService;

public class WarehouseController {

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

    private static final BuyingAutoService<AutoPartResult>
            BUYING_AUTO_SERVICE = new AutoPartService(MainApp.getFactory());

    public WarehouseController() {
    }

    /**
     * Инициализация класса-контроллера. Этот метод вызывается автоматически
     * после того, как fxml-файл будет загружен.
     */
    @FXML
    private void initialize() {
        // Инициализация таблицы автозапчастей с четырьмя столбцами.
        ObservableList<AutoPartResult> autoPartResults = BUYING_AUTO_SERVICE.getAllEntityResults();
        autoPartTable.setItems(autoPartResults);

        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        inStockColumn.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());
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
            inStockLabel.setText(Integer.toString(autoPartResult.getAmount()));
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
     * Вызывается, когда пользователь нажимает кнопку "Добавить"
     * Открывает диалоговое окно с дополнительной информацией новой автозапчасти
     */
    @FXML
    private void handleNewAutoPart() {
        AutoPartResult tempAutoPartResult = new AutoPartResult();
        boolean okClicked = MainApp.showAutoPartEditDialog(tempAutoPartResult);
        if (okClicked) {
            BUYING_AUTO_SERVICE.saveEntityResult(tempAutoPartResult);
            initialize();
        }
    }

    /**
     * Вызывается, когда пользователь нажимает кнопку "Изменить"
     * Открывает диалоговое окно для изменения выбранной автозапчасти.
     */
    @FXML
    private void handleEditAutoPart() {
        AutoPartResult selectedAutoPartResult = autoPartTable.getSelectionModel().getSelectedItem();
        if (selectedAutoPartResult != null) {
            boolean okClicked = MainApp.showAutoPartEditDialog(selectedAutoPartResult);
            if (okClicked) {
                BUYING_AUTO_SERVICE.saveEntityResult(selectedAutoPartResult);
                initialize();
            }

        } else {
            // Ничего не выбрано.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(MainApp.getPrimaryStage());
            alert.setTitle("Внимание!");
            alert.setHeaderText("Никто не выбран");
            alert.setContentText("Пожалуйста, выберите человека в таблице.");

            alert.showAndWait();
        }
    }

    /**
     * Вызывается, когда пользователь нажимает кнопку "Удалить"
     */
    @FXML
    private void handleDeleteAutoPart() {
        int selectedIndex = autoPartTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            BUYING_AUTO_SERVICE.deleteEntityResult(
                    autoPartTable.getSelectionModel().getSelectedItem().getVendorCode());
            initialize();
        } else {
            // Ничего не выбрано.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(MainApp.getPrimaryStage());
            alert.setTitle("Внимание!");
            alert.setHeaderText("Никто не выбран");
            alert.setContentText("Пожалуйста, выберите человека в таблице.");

            alert.showAndWait();
        }
    }
}
