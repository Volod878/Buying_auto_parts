package ru.volod878.buying_auto_parts.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.volod878.buying_auto_parts.entity.AutoPart;

import java.util.List;

public class AutoPartDAO implements BuyingAutoDAO<AutoPart> {

    private final SessionFactory factory;


    public AutoPartDAO(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public List<AutoPart> getAllEntities() {
        List<AutoPart> allAutoParts;

        try(Session session = factory.getCurrentSession()) {
            session.beginTransaction();

            allAutoParts = session.createQuery("from AutoPart", AutoPart.class)
                    .getResultList();

            session.getTransaction().commit();
        }

        return allAutoParts;
    }

    @Override
    public void saveEntity(AutoPart autoPart) {
        try(Session session = factory.getCurrentSession()) {
            session.beginTransaction();

            session.saveOrUpdate(autoPart);

            session.getTransaction().commit();
        }
    }

    @Override
    public AutoPart getEntity(int id) {
        AutoPart autoPart;

        try(Session session = factory.getCurrentSession()) {
            session.beginTransaction();

            autoPart = session.get(AutoPart.class, id);

            session.getTransaction().commit();
        }

        return autoPart;
    }

    @Override
    public void deleteEntity(int id) {
        try(Session session = factory.getCurrentSession()) {
            session.beginTransaction();

            Query<AutoPart> query = session.createQuery("delete from AutoPart where id=:autoPartId");
            query.setParameter("autoPartId", id);
            query.executeUpdate();

            session.getTransaction().commit();
        }
    }
}
