package ru.volod878.buying_auto_parts.view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ru.volod878.buying_auto_parts.MainApp;
import ru.volod878.buying_auto_parts.model.CustomerResult;
import ru.volod878.buying_auto_parts.model.OrderResult;
import ru.volod878.buying_auto_parts.service.BuyingAutoService;
import ru.volod878.buying_auto_parts.service.CustomerService;

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
    public TableColumn<OrderResult, String> dateColumn;
    @FXML
    public TableColumn<OrderResult, String> statusColumn;

    private static final BuyingAutoService<CustomerResult>
            BUYING_AUTO_SERVICE = new CustomerService(MainApp.getFactory());

    public CustomerController() {
    }

    @FXML
    private void initialize() {
        // Инициализация таблицы клиентов.
        ObservableList<CustomerResult> allCustomerResults = BUYING_AUTO_SERVICE.getAllEntityResults();
        customerTable.setItems(allCustomerResults);

        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        ordersColumn.setCellValueFactory(cellData -> cellData.getValue().numberOfOrdersProperty().asObject());

        // Слушаем изменения выбора, и при изменении отображаем
        // дополнительную информацию об автозапчасти.
        customerTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showOrderDetails(newValue));
    }

    /**
     * Заполняет все текстовые поля, отображая подробности о заказах клиента.
     * Если у клиента заказов нет, то все текстовые поля очищаются
     */
    private void showOrderDetails(CustomerResult customerResult) {
        if (customerResult != null && customerResult.getAllOrders() != null) {
            // Заполняем метки информацией из объекта AutoPart.
            ObservableList<OrderResult> allOrders = customerResult.getAllOrders();
            ordersTable.setItems(allOrders);

            numberColumn.setCellValueFactory(cellData -> cellData.getValue().numberProperty().asObject());
            totalCostColumn.setCellValueFactory(cellData -> cellData.getValue().totalCostProperty().asObject());
            dateColumn.setCellValueFactory(cellData -> cellData.getValue().dataProperty());
            statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        }
    }
}
