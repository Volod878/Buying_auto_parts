package ru.volod878.buying_auto_parts.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.volod878.buying_auto_parts.entity.Order;

import java.util.List;

public class OrderDAO implements BuyingAutoDAO<Order> {

    private final SessionFactory factory;


    public OrderDAO(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public List<Order> getAllEntities() {
        List<Order> allOrders;

        try(Session session = factory.getCurrentSession()) {
            session.beginTransaction();

            allOrders = session.createQuery("from Order", Order.class)
                    .getResultList();

            session.getTransaction().commit();
        }

        return allOrders;
    }

    @Override
    public void saveEntity(Order order) {
        try(Session session = factory.getCurrentSession()) {
            session.beginTransaction();

            session.saveOrUpdate(order);

            session.getTransaction().commit();
        }
    }

    @Override
    public Order getEntity(int id) {
        Order order;

        try(Session session = factory.getCurrentSession()) {
            session.beginTransaction();

            order = session.get(Order.class, id);

            session.getTransaction().commit();
        }

        return order;
    }

    @Override
    public void deleteEntity(int id) {
        try(Session session = factory.getCurrentSession()) {
            session.beginTransaction();

            Order order = session.get(Order.class, id);
            session.delete(order);

            session.getTransaction().commit();
        }
    }
}
