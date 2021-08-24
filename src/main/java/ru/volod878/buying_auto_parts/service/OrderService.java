package ru.volod878.buying_auto_parts.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.SessionFactory;
import ru.volod878.buying_auto_parts.dao.BuyingAutoDAO;
import ru.volod878.buying_auto_parts.dao.OrderDAO;
import ru.volod878.buying_auto_parts.entity.Order;
import ru.volod878.buying_auto_parts.model.OrderResult;

import java.util.List;

/**
 * Класс отвечает за работу с сущностями Order и моделями OrderResult.
 * Данный класс адаптирует эти объекты между собой
 */
public class OrderService implements BuyingAutoService<OrderResult> {

    private final BuyingAutoDAO<Order> buyingAutoDAO;


    public OrderService(SessionFactory factory) {
        this.buyingAutoDAO = new OrderDAO(factory);
    }

    @Override
    public ObservableList<OrderResult> getAllEntityResults() {
        List<Order> allOrders = buyingAutoDAO.getAllEntities();
        ObservableList<OrderResult> allOrderResult = FXCollections.observableArrayList();
        for (Order order: allOrders) allOrderResult.add(new OrderResult(order));
        return allOrderResult;
    }

    @Override
    public void saveEntityResult(OrderResult orderResult) {
        Order order = new Order(orderResult);
        buyingAutoDAO.saveEntity(order);
    }

    @Override
    public OrderResult getEntityResult(int id) {
        Order order = buyingAutoDAO.getEntity(id);
        OrderResult orderResult = new OrderResult(order);
        orderResult.setAllPurchases(order.getShoppingCart());
        return orderResult;
    }

    @Override
    public void deleteEntityResult(int id) {
        buyingAutoDAO.deleteEntity(id);
    }
}
