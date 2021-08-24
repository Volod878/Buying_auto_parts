package ru.volod878.buying_auto_parts.view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ru.volod878.buying_auto_parts.MainApp;
import ru.volod878.buying_auto_parts.model.AutoPartResult;
import ru.volod878.buying_auto_parts.model.ShopResult;
import ru.volod878.buying_auto_parts.service.AutoPartService;
import ru.volod878.buying_auto_parts.service.BuyingAutoService;
import ru.volod878.buying_auto_parts.service.ShopService;
import ru.volod878.buying_auto_parts.util.GoodsDelivery;


/**
 * Класс-контроллер.
 * Управляет всеми действиями на складе
 */
public class WarehouseController {

    @FXML
    private TableView<AutoPartResult> autoPartTable;
    @FXML
    private TableColumn<AutoPartResult, String> nameColumn;
    @FXML
    private TableColumn<AutoPartResult, Double> priceColumn;
    @FXML
    private TableColumn<AutoPartResult, Integer> amountColumn;
    @FXML
    private TableColumn<AutoPartResult, Integer> deliveryPeriodColumn;

    @FXML
    private Label vendorCodeLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label amountLabel;
    @FXML
    private Label deliveryPeriodLabel;

    @FXML
    public Spinner<Integer> spinner;

    private static final BuyingAutoService<AutoPartResult>
            AUTO_PART_SERVICE = new AutoPartService(MainApp.getFactory());
    private static final BuyingAutoService<ShopResult>
            SHOP_SERVICE = new ShopService(MainApp.getFactory());

    /**
     * Инициализация класса-контроллера
     */
    @FXML
    private void initialize() {
        // Инициализация таблицы автозапчастей с четырьмя столбцами.
        ObservableList<AutoPartResult> autoPartResults = AUTO_PART_SERVICE.getAllEntityResults();
        autoPartTable.setItems(autoPartResults);

        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        amountColumn.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());
        deliveryPeriodColumn.setCellValueFactory(cellData
                -> cellData.getValue().deliveryPeriodProperty().asObject());

        // Очистка дополнительной информации
        showAutoPartDetails(null);

        // Слушаем изменения выбора, и при изменении отображаем
        // дополнительную информацию об автозапчасти
        autoPartTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showAutoPartDetails(newValue));
    }

    /**
     * Заполняет все текстовые поля, отображая подробности об автозапчасти.
     * Если указанная автозапчасть = null, то все текстовые поля очищаются
     */
    private void showAutoPartDetails(AutoPartResult autoPartResult) {
        if (autoPartResult != null) {
            // Заполняем метки информацией из объекта AutoPartResult
            nameLabel.setText(autoPartResult.getName());
            priceLabel.setText(Double.toString(autoPartResult.getPrice()));
            amountLabel.setText(Integer.toString(autoPartResult.getAmount()));
            deliveryPeriodLabel.setText(Integer.toString(autoPartResult.getDeliveryPeriod()));
            vendorCodeLabel.setText(Integer.toString(autoPartResult.getVendorCode()));

            // Устанавливаем максимальное количество автозапчастей,
            // которое можно выбрать для отправки в магазин.
            // Максимальное значение равно количеству выбранной автозапчасти
            // которые есть на складе
            int maxValue = autoPartResult.getAmount();
            SpinnerValueFactory<Integer> valueFactory =
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(0, maxValue, 0);
            spinner.setValueFactory(valueFactory);
        } else {
            // Отчищаем поля
            nameLabel.setText("");
            priceLabel.setText("");
            amountLabel.setText("");
            deliveryPeriodLabel.setText("");
            vendorCodeLabel.setText("");
        }
    }

    /**
     * Вызывается, когда пользователь нажимает кнопку "В магазин"
     * Выбранное количество оправляется в магазин
     */
    @FXML
    public void handleInShop() {
        // Инициализируем автозапчасть, которую оправляем в магазин
        AutoPartResult selectedAutoPartResult = autoPartTable.getSelectionModel().getSelectedItem();
        int number = spinner.getValue();

        // Поля для сообщения об отправке
        String title, headerText, contentText;
        Alert alert;

        if (selectedAutoPartResult != null && number != 0) {
            // Уменьшаем количество автозапчастей на складе и сохраняем в БД
            selectedAutoPartResult.setAmount(selectedAutoPartResult.getAmount() - number);
            AUTO_PART_SERVICE.saveEntityResult(selectedAutoPartResult);

            // Отправляем товар в магазин отдельным потоком
            GoodsDelivery goodsDelivery = new GoodsDelivery();
            goodsDelivery.setAutoPartResult(selectedAutoPartResult);
            goodsDelivery.setShopService(SHOP_SERVICE);
            goodsDelivery.setNumber(number);
            Thread deliveryThread = new Thread(goodsDelivery);
            deliveryThread.start();

            // Информационное сообщение об удачной отправке товара
            alert = new Alert(Alert.AlertType.INFORMATION);
            title = "Служба доставки";
            headerText = "Товар отправлен в магазин";
            contentText = "Ожидаемый срок доставки: " + selectedAutoPartResult.getDeliveryPeriod() + " сек.";

            initialize();
        } else {
            // Если автозапчасть не выбрана
            alert = new Alert(Alert.AlertType.WARNING);
            title = "Внимание!";
            headerText = "Не выбраны автозапчасть или количество";
            contentText = "Пожалуйста, выберите автозапчасть и укажите количество";
        }

        // Отображение информационного сообщения на экране
        alert.initOwner(MainApp.getPrimaryStage());
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    /**
     * Вызывается, когда пользователь нажимает кнопку "Добавить"
     * Открывает диалоговое окно с дополнительной информацией новой автозапчасти
     */
    @FXML
    private void handleNewAutoPart() {
        AutoPartResult tempAutoPartResult = new AutoPartResult();
        // Слушаем изменения выбора.
        // Если true - сохраняем в БД
        boolean okClicked = MainApp.showAutoPartEditDialog(tempAutoPartResult);
        if (okClicked) {
            AUTO_PART_SERVICE.saveEntityResult(tempAutoPartResult);
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
            // Слушаем изменения выбора.
            // Если true - сохраняем в БД
            boolean okClicked = MainApp.showAutoPartEditDialog(selectedAutoPartResult);
            if (okClicked) {
                AUTO_PART_SERVICE.saveEntityResult(selectedAutoPartResult);
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
        // Если автозапчасть выбрана, удаляем ем ее из БД
        if (selectedIndex >= 0) {
            AUTO_PART_SERVICE.deleteEntityResult(
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
