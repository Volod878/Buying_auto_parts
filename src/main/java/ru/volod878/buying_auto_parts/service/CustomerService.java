package ru.volod878.buying_auto_parts.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.SessionFactory;
import ru.volod878.buying_auto_parts.dao.BuyingAutoDAO;
import ru.volod878.buying_auto_parts.dao.CustomerDAO;
import ru.volod878.buying_auto_parts.entity.*;
import ru.volod878.buying_auto_parts.model.CustomerResult;
import ru.volod878.buying_auto_parts.model.OrderResult;
import ru.volod878.buying_auto_parts.util.Status;

import java.util.List;

/**
 * Класс отвечает за работу с сущностями Customer и моделями CustomerResult.
 * Данный класс адаптирует эти объекты между собой
 */
public class CustomerService implements BuyingAutoService<CustomerResult> {

    private final BuyingAutoDAO<Customer> buyingAutoDAO;

    public CustomerService(SessionFactory factory) {
        this.buyingAutoDAO = new CustomerDAO(factory);
    }

    @Override
    public ObservableList<CustomerResult> getAllEntityResults() {
        List<Customer> allCustomers = buyingAutoDAO.getAllEntities();
        ObservableList<CustomerResult> customerResults = FXCollections.observableArrayList();
        for (Customer customer: allCustomers) customerResults.add(new CustomerResult(customer));
        return customerResults;
    }

    @Override
    public void saveEntityResult(CustomerResult customerResult) {
        Customer customer = new Customer(customerResult);

        // Находим все заказы которые не привязаны к клиентам
        // и связываем их между собой
        if (customerResult.getAllOrders().size() > 0) {
            int size = customerResult.getAllOrders().size();
            for (int i = size-1; i >=0; i--) {
                OrderResult orderResult = customerResult.getAllOrders().get(i);
                if (orderResult.getCustomerResult() != null) {
                    Order order = new Order(orderResult);
                    order.setCustomer(customer);
                    customer.addOrder(order);

                    // Если в текущий момент несколько активных заказов,
                    // то в момент создания заказа нужно сохранить только последний
                    if (orderResult.getStatus().equals(Status.EXECUTION.getName())
                            && orderResult.getNumber() == 0) break;
                }
            }
        }
        buyingAutoDAO.saveEntity(customer);
    }

    @Override
    public CustomerResult getEntityResult(int id) {
        Customer customer = buyingAutoDAO.getEntity(id);
        return new CustomerResult(customer);
    }

    @Override
    public void deleteEntityResult(int id) {
        buyingAutoDAO.deleteEntity(id);
    }
}
