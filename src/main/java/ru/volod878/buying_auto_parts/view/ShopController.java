package ru.volod878.buying_auto_parts.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import ru.volod878.buying_auto_parts.MainApp;
import ru.volod878.buying_auto_parts.model.*;
import ru.volod878.buying_auto_parts.service.BuyingAutoService;
import ru.volod878.buying_auto_parts.service.CustomerService;
import ru.volod878.buying_auto_parts.service.ShopService;
import ru.volod878.buying_auto_parts.util.GoodsDelivery;
import ru.volod878.buying_auto_parts.util.Status;

/**
 * Класс контроллер.
 * Управляет всеми действиями в магазине
 */
public class ShopController {
    @FXML
    public Label customerLabel;

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

    private static final BuyingAutoService<CustomerResult>
            CUSTOMER_SERVICE = new CustomerService(MainApp.getFactory());
    private static final BuyingAutoService<ShopResult>
            SHOP_SERVICE = new ShopService(MainApp.getFactory());

    private CustomerResult storeUser;
    private OrderResult orderResult = new OrderResult();
    private ObservableList<ShopResult> shopResults;

    /**
     * Инициализация таблицы с авто запчастями
     */
    public void initController() {
        customerLabel.setText(storeUser.getName());

        // Инициализация таблицы автозапчастей с четырьмя столбцами.
        shopResults = SHOP_SERVICE.getAllEntityResults();
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
     * Устанавливаем клиента с которым будет работа
     */
    public void setCustomer(CustomerResult customer) {
        // Инициализация клиента с которым идет работа (по умолчанию - Администратор)
        if (customer.getName() == null) this.storeUser = CUSTOMER_SERVICE.getEntityResult(1);
        else this.storeUser = customer;
    }

    /**
     * Заполняет все текстовые поля, отображая подробности об автозапчасти.
     * Если указанная автозапчасть = null, то все текстовые поля очищаются
     */
    private void showShopDetails(ShopResult shopResult) {
        if (shopResult != null) {
            // Заполняем метки информацией из объекта ShopResult.
            nameLabel.setText(shopResult.getName());
            priceLabel.setText(Double.toString(shopResult.getPrice()));
            inStockLabel.setText(Integer.toString(shopResult.getInStock()));
            deliveryPeriodLabel.setText(Integer.toString(shopResult.getDeliveryPeriod()));
            vendorCodeLabel.setText(Integer.toString(shopResult.getVendorCode()));

            // Устанавливаем максимальное количество товара которое можно выбрать
            // равное их количеству в магазине
            int maxValue = shopResult.getInStock();

            // Вычитаем то что добавили в корзину
            if (orderResult.getAllPurchases().size() > 0)
                for (ShoppingCartResult shoppingCartResult: orderResult.getAllPurchases())
                    if (shoppingCartResult.getName().equals(shopResult.getName()))
                        maxValue -= shoppingCartResult.getAmount();


                    // Присваиваем новое значение спиннеру с учетом товара в корзине
            SpinnerValueFactory<Integer> valueFactory =
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(0, maxValue, 0);
            spinner.setValueFactory(valueFactory);
        } else {
            // Если автозапчасть не выбрана, очищаем данные
            nameLabel.setText("");
            priceLabel.setText("");
            inStockLabel.setText("");
            deliveryPeriodLabel.setText("");
            vendorCodeLabel.setText("");
            SpinnerValueFactory<Integer> valueFactory =
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 0, 0);
            spinner.setValueFactory(valueFactory);
        }
    }

    /**
     * Вызывается, когда пользователь нажимает кнопку "В корзину"
     */
    @FXML
    public void handleAddAutoPart() {
        // Инициализируем автозапчасть для добавления в корзину
        ShopResult selectedShopResult = autoPartOfShopTable.getSelectionModel().getSelectedItem();
        int number = spinner.getValue();
        // Проверяем выбор клиента, если все верно добавляем в корзину
        if (selectedShopResult != null && number != 0) {
            orderResult.addPurchases(selectedShopResult, number);
            initController();
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
     * Открывает окно для оплаты выбранных автозапчастей.
     */
    @FXML
    public void handleShoppingCart() {
        orderResult.setCustomerResult(storeUser);

        // Слушаем статус оплаты
        // Если оплата прошла, формируем заказ и отправляем клиенту
        boolean okClicked = MainApp.showShoppingCardDialog(orderResult);
        if (okClicked) {
            orderResult.setStatus(Status.EXECUTION.getName());
            storeUser.addOrder(orderResult);
            storeUser.setNumberOfOrders(storeUser.getAllOrders().size());
            // Уменьшить количество запчастей в магазине
            ObservableList<ShopResult>  tempShopResults = FXCollections.observableArrayList();
            for (ShoppingCartResult shoppingCartResult: orderResult.getAllPurchases())
                for (ShopResult shopResult: shopResults)
                    if (shopResult.getName().equals(shoppingCartResult.getName())) {
                        shopResult.setInStock(shopResult.getInStock() - shoppingCartResult.getAmount());
                        tempShopResults.add(shopResult);
                    }

            // Отправляем товар клиенту отдельным потоком
            GoodsDelivery goodsDelivery = new GoodsDelivery();
            goodsDelivery.setShopResults(tempShopResults);
            goodsDelivery.setCustomerService(CUSTOMER_SERVICE);
            goodsDelivery.setCustomerResult(storeUser);
            goodsDelivery.setOrderResult(orderResult);
            Thread deliveryThread = new Thread(goodsDelivery);
            deliveryThread.start();

            // Сохраняем заказ в БД и создаем новый
            tempShopResults.forEach(SHOP_SERVICE::saveEntityResult);
            CUSTOMER_SERVICE.saveEntityResult(storeUser);
            orderResult = new OrderResult();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initOwner(MainApp.getPrimaryStage());
            alert.setTitle("Служба доставки");
            alert.setHeaderText("Товар отправлен клиенту");
            alert.setContentText("Ожидаемый срок доставки: " + goodsDelivery.getDeliveryPeriod() + " сек.");
            alert.showAndWait();

            initController();
        }
    }
}
