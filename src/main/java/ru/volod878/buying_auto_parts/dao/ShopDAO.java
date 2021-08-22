package ru.volod878.buying_auto_parts.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.volod878.buying_auto_parts.entity.Shop;

import java.util.List;

public class ShopDAO implements BuyingAutoDAO<Shop> {

    private final SessionFactory factory;


    public ShopDAO(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public List<Shop> getAllEntities() {
        List<Shop> allAutoPartsOfShop;

        try(Session session = factory.getCurrentSession()) {
            session.beginTransaction();

            allAutoPartsOfShop = session.createQuery("from Shop", Shop.class)
                    .getResultList();

            session.getTransaction().commit();
        }

        return allAutoPartsOfShop;
    }

    @Override
    public void saveEntity(Shop shop) {
        try(Session session = factory.getCurrentSession()) {
            session.beginTransaction();

            Shop tempShop = session.get(Shop.class, shop.getId());
            tempShop.setInStock(shop.getInStock());

            session.getTransaction().commit();
        }
    }

    @Override
    public Shop getEntity(int id) {
        Shop shop;

        try(Session session = factory.getCurrentSession()) {
            session.beginTransaction();

            shop = session.get(Shop.class, id);

            session.getTransaction().commit();
        }

        return shop;
    }

    @Override
    public void deleteEntity(int id) {
        try(Session session = factory.getCurrentSession()) {
            session.beginTransaction();

            Shop autoPartOfShop = session.get(Shop.class, id);
            session.delete(autoPartOfShop);

            session.getTransaction().commit();
        }
    }
}
