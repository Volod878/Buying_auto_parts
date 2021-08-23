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

    public static final BuyingAutoService<CustomerResult>
            CUSTOMER_SERVICE = new CustomerService(MainApp.getFactory());

    private MainApp mainApp;

    private CustomerResult customerResult;
    private OrderResult orderResult;

    public CustomerController() {
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }


    @FXML
    private void initialize() {
        // Инициализация таблицы клиентов.
        ObservableList<CustomerResult> allCustomerResults = CUSTOMER_SERVICE.getAllEntityResults();
        customerTable.setItems(allCustomerResults);

        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        ordersColumn.setCellValueFactory(cellData -> cellData.getValue().numberOfOrdersProperty().asObject());

        // Слушаем изменения выбора, и при изменении отображаем
        // дополнительную информацию об автозапчасти.
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
            // Заполняем метки информацией из объекта AutoPart.
            ObservableList<OrderResult> allOrders = customerResult.getAllOrders();
            ordersTable.setItems(allOrders);

            numberColumn.setCellValueFactory(cellData -> cellData.getValue().numberProperty().asObject());
            totalCostColumn.setCellValueFactory(cellData -> cellData.getValue().totalCostProperty().asObject());
            dateColumn.setCellValueFactory(cellData -> cellData.getValue().dataProperty());
            statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

            ordersTable.getSelectionModel().selectedItemProperty().addListener(
                    (observable, oldValue, newValue) -> this.orderResult = newValue);

        }
    }

    @FXML
    public void handleDetails() {
        if (orderResult != null) {
            orderResult.setCustomerResult(customerResult);
            MainApp.showPurchaseDetails(orderResult);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(MainApp.getPrimaryStage());
            alert.setTitle("Внимание!");
            alert.setHeaderText("Не выбран заказ");
            alert.setContentText("Пожалуйста, выберите заказ из таблицы");

            alert.showAndWait();
        }
    }

    @FXML
    public void handleAddNewCustomer() {
        CustomerResult tempCustomerResult = new CustomerResult();
        boolean okClicked = MainApp.showAddNewCustomerDialog(tempCustomerResult);
        if (okClicked) {
            CUSTOMER_SERVICE.saveEntityResult(tempCustomerResult);
            initialize();
        }
    }

    @FXML
    public void handleChoiceCustomer() {
        if (customerResult != null) mainApp.showShop(customerResult);
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(MainApp.getPrimaryStage());
            alert.setTitle("Внимание!");
            alert.setHeaderText("Не выбран клиент");
            alert.setContentText("Пожалуйста, выберите клиента из списка");

            alert.showAndWait();
        }
    }
}
