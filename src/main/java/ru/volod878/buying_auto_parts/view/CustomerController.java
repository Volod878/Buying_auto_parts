package ru.volod878.buying_auto_parts.view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ru.volod878.buying_auto_parts.MainApp;
import ru.volod878.buying_auto_parts.model.CustomerResult;
import ru.volod878.buying_auto_parts.model.OrderResult;
import ru.volod878.buying_auto_parts.service.BuyingAutoService;
import ru.volod878.buying_auto_parts.service.CustomerService;

import java.time.LocalDateTime;

/**
 * Класс-контроллер.
 * Управляет всеми действиями с клиентами
 */
public class CustomerController {
    @FXML
    public TableView<CustomerResult> customerTable;
    @FXML
    public TableColumn<CustomerResult, String> nameColumn;
    @FXML
    public TableColumn<CustomerResult, Integer> ordersColumn;

    @FXML
    public TableView<OrderResult> ordersTable;
    @FXML
    public TableColumn<OrderResult, Integer> numberColumn;
    @FXML
    public TableColumn<OrderResult, Double> totalCostColumn;
    @FXML
    public TableColumn<OrderResult, LocalDateTime> dateColumn;
    @FXML
    public TableColumn<OrderResult, String> statusColumn;

    public static final BuyingAutoService<CustomerResult>
            CUSTOMER_SERVICE = new CustomerService(MainApp.getFactory());

    private MainApp mainApp;

    private CustomerResult customerResult;
    private OrderResult orderResult;

    /**
     * Вызывается главным приложением, которое даёт на себя ссылку
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Инициализация класса-контроллера.
     */
    @FXML
    private void initialize() {
        // Инициализация таблицы клиентов
        ObservableList<CustomerResult> allCustomerResults = CUSTOMER_SERVICE.getAllEntityResults();
        customerTable.setItems(allCustomerResults);

        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        ordersColumn.setCellValueFactory(cellData -> cellData.getValue().numberOfOrdersProperty().asObject());

        // Слушаем изменения выбора, и при изменении отображаем
        // дополнительную информацию о заказах клиента
        customerTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    this.customerResult = newValue;
                    showOrderDetails(newValue);
                });
    }

    /**
     * Заполняет все текстовые поля, отображая подробности о заказах клиента.
     * Если у клиента заказов нет, то все текстовые поля очищаются
     */
    private void showOrderDetails(CustomerResult customerResult) {
        if (customerResult != null && customerResult.getAllOrders() != null) {
            // Заполняем таблицу информацией из объекта OrderResult принадлежащего клиенту
            ObservableList<OrderResult> allOrders = customerResult.getAllOrders();
            ordersTable.setItems(allOrders);

            numberColumn.setCellValueFactory(cellData -> cellData.getValue().numberProperty().asObject());
            totalCostColumn.setCellValueFactory(cellData -> cellData.getValue().totalCostProperty().asObject());
            dateColumn.setCellValueFactory(cellData -> cellData.getValue().purchaseDateProperty());
            statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

            // Слушаем изменения выбора, и при изменении присваиваем ссылку
            // на заказ полу orderResult
            ordersTable.getSelectionModel().selectedItemProperty().addListener(
                    (observable, oldValue, newValue) -> this.orderResult = newValue);

        }
    }

    /**
     * Срабатывает при нажатии кнопки Подробнее.
     * Если orderResult не null
     * открываем окно с подробной информацией о заказе
     */
    @FXML
    public void handleDetails() {
        if (orderResult != null) {
            orderResult.setCustomerResult(customerResult);
            MainApp.showPurchaseDetails(orderResult);
        }
        else {
            // Предупреждаем что нужно выбрать заказ
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(MainApp.getPrimaryStage());
            alert.setTitle("Внимание!");
            alert.setHeaderText("Не выбран заказ");
            alert.setContentText("Пожалуйста, выберите заказ из таблицы");

            alert.showAndWait();
        }
    }

    /**
     * Срабатывает при нажатии кнопки Добавить.
     * Открывает диалоговое окно для добавления нового клиента
     */
    @FXML
    public void handleAddNewCustomer() {
        CustomerResult tempCustomerResult = new CustomerResult();
        boolean okClicked = MainApp.showAddNewCustomerDialog(tempCustomerResult);
        if (okClicked) {
            CUSTOMER_SERVICE.saveEntityResult(tempCustomerResult);
            initialize();
        }
    }

    /**
     * Срабатывает при нажатии кнопки Выбрать.
     * Если customerResult не null, преходим в магазин
     * для совершения новых покупок текущему клиенту
     */

    @FXML
    public void handleChoiceCustomer() {
        if (customerResult != null) mainApp.showShop(customerResult);
        else {
            // Если клиент не выбран
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(MainApp.getPrimaryStage());
            alert.setTitle("Внимание!");
            alert.setHeaderText("Не выбран клиент");
            alert.setContentText("Пожалуйста, выберите клиента из списка");

            alert.showAndWait();
        }
    }
}
