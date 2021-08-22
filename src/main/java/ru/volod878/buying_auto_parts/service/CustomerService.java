package ru.volod878.buying_auto_parts.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.SessionFactory;
import ru.volod878.buying_auto_parts.dao.BuyingAutoDAO;
import ru.volod878.buying_auto_parts.dao.CustomerDAO;
import ru.volod878.buying_auto_parts.entity.*;
import ru.volod878.buying_auto_parts.model.CustomerResult;
import ru.volod878.buying_auto_parts.model.OrderResult;

import java.util.List;

public class CustomerService implements BuyingAutoService<CustomerResult> {

    private final BuyingAutoDAO<Customer> buyingAutoDAO;

    private Customer customer;

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

        int lastItem = customerResult.getAllOrders().size()-1;
        OrderResult lastOrderResult = customerResult.getAllOrders().get(lastItem);

        Order lastOrder = new Order(lastOrderResult);
        lastOrder.setCustomer(customer);
        customer.addOrder(lastOrder);

        buyingAutoDAO.saveEntity(customer);
    }

    @Override
    public CustomerResult getEntityResult(int id) {
        customer = buyingAutoDAO.getEntity(id);
        return new CustomerResult(customer);
    }

    @Override
    public void deleteEntityResult(int id) {
        buyingAutoDAO.deleteEntity(id);
    }
}
