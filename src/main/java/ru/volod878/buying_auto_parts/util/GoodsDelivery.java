package ru.volod878.buying_auto_parts.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.volod878.buying_auto_parts.model.AutoPartResult;
import ru.volod878.buying_auto_parts.model.CustomerResult;
import ru.volod878.buying_auto_parts.model.OrderResult;
import ru.volod878.buying_auto_parts.model.ShopResult;
import ru.volod878.buying_auto_parts.service.BuyingAutoService;

/**
 * Класс отвечающий за доставку товара в магазин и клиентам
 */
public class GoodsDelivery implements Runnable {

    private BuyingAutoService<ShopResult> shopService;

    private BuyingAutoService<CustomerResult> customerService;

    private final ObservableList<ShopResult> shopResults = FXCollections.observableArrayList();

    private AutoPartResult autoPartResult;

    private CustomerResult customer;
    private OrderResult orderResult;

    private int deliveryPeriod;
    private int number;

    public GoodsDelivery() {
    }

    @Override
    public void run() {
        try {
            Thread.sleep(deliveryPeriod * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Выполняться когда отправляем автозапчасти клиенту
        if (customer != null) {
            orderResult.setStatus(Status.COMPLETED.getName());
            CustomerResult tempCustomer = customerService.getEntityResult(customer.getId());

            OrderResult newOrderResult = new OrderResult();
            newOrderResult.setStatus(Status.COMPLETED.getName());
            newOrderResult.setPurchaseDate(orderResult.getPurchaseDate());
            newOrderResult.setTotalCost(orderResult.getTotalCost());

            CustomerResult newCustomer = new CustomerResult();
            newCustomer.setId(customer.getId());
            newCustomer.setName(customer.getName());
            newCustomer.setNumberOfOrders(customer.getNumberOfOrders());
            newCustomer.addOrder(newOrderResult);

            newOrderResult.setCustomerResult(newCustomer);
            setNumberInOrder(tempCustomer, newOrderResult);

            customerService.saveEntityResult(newCustomer);
        }

        // Выполняться когда отправляем автозапчасти со склада в магазин
        if (autoPartResult != null) {
            // Получаем текущее количество товара в магазине
            ShopResult shopResult = shopService.getEntityResult(autoPartResult.getVendorCode());
            // Сохраняем в БД обновленные данные
            shopResult.setInStock(shopResult.getInStock() + number);
            shopService.saveEntityResult(shopResult);
        }
    }

    /**
     * Находим текущий заказ клиента со статусом 'В пути'
     * Устанавливаем ему id для корректного сохранения в БД
     */
    private synchronized void setNumberInOrder(CustomerResult customerResult, OrderResult thisOrderResult) {
        customerResult.getAllOrders().stream()
                .filter(o -> o.getPurchaseDate().equals(thisOrderResult.getPurchaseDate()))
                .filter(o -> o.getTotalCost() == thisOrderResult.getTotalCost())
                .filter(o -> customerResult.getName().equals(thisOrderResult.getCustomerResult().getName()))
                .findFirst()
                .ifPresent(o -> thisOrderResult.setNumber(o.getNumber()));
    }

    /**
     * Устанавливаем список автозапчастей из магазина, который оплатил клиент.
     * Определяем срок доставки по максимальному значению
     */
    public void setShopResults(ObservableList<ShopResult> shopResults) {
        shopResults.stream()
                .peek(this.shopResults::add)
                .mapToInt(ShopResult::getDeliveryPeriod)
                .max()
                .ifPresent(deliveryPeriod -> this.deliveryPeriod = deliveryPeriod);

    }

    public void setShopService(BuyingAutoService<ShopResult> shopService) {
        this.shopService = shopService;
    }

    public void setCustomerService(BuyingAutoService<CustomerResult> customerService) {
        this.customerService = customerService;
    }

    public void setAutoPartResult(AutoPartResult autoPartResult) {
        this.autoPartResult = autoPartResult;
        this.deliveryPeriod = autoPartResult.getDeliveryPeriod();
    }

    public void setCustomerResult(CustomerResult customer) {
        this.customer = customer;
    }

    public void setOrderResult(OrderResult orderResult) {
        this.orderResult = orderResult;
    }

    public int getDeliveryPeriod() {
        return deliveryPeriod;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
