package ru.volod878.buying_auto_parts.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.volod878.buying_auto_parts.entity.Customer;

import java.util.List;

public class CustomerDAO implements BuyingAutoDAO<Customer> {

    private final SessionFactory factory;


    public CustomerDAO(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public List<Customer> getAllEntities() {
        List<Customer> allCustomers;

        try(Session session = factory.getCurrentSession()) {
            session.beginTransaction();

            allCustomers = session.createQuery("from Customer", Customer.class)
                    .getResultList();

            session.getTransaction().commit();
        }

        return allCustomers;
    }

    @Override
    public void saveEntity(Customer customer) {
        try(Session session = factory.getCurrentSession()) {
            session.beginTransaction();

            session.saveOrUpdate(customer);

            session.getTransaction().commit();
        }
    }

    @Override
    public Customer getEntity(int id) {
        Customer customer;

        try(Session session = factory.getCurrentSession()) {
            session.beginTransaction();


            Query<Customer> query = session.createQuery("from Customer where id=:customerId");
            query.setParameter("customerId", id);
            customer = query.getSingleResult();

            session.getTransaction().commit();
        }

        return customer;
    }

    @Override
    public void deleteEntity(int id) {
        try(Session session = factory.getCurrentSession()) {
            session.beginTransaction();

            Customer customer = session.get(Customer.class, id);
            session.delete(customer);

            session.getTransaction().commit();
        }
    }
}
