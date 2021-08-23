package ru.volod878.buying_auto_parts.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.SessionFactory;
import ru.volod878.buying_auto_parts.dao.BuyingAutoDAO;
import ru.volod878.buying_auto_parts.dao.CustomerDAO;
import ru.volod878.buying_auto_parts.entity.*;
import ru.volod878.buying_auto_parts.model.CustomerResult;

import java.util.List;

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
        int lastOrder = customerResult.getAllOrders().size() - 1;
        if (lastOrder >= 0) {
            Order order = new Order(customerResult.getAllOrders().get(lastOrder));
            order.setCustomer(customer);
            customer.addOrder(order);
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
