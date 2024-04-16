package ru.itmo.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.itmo.entity.Kitty;
import ru.itmo.entity.Owner;
import ru.itmo.models.Breed;
import ru.itmo.models.Colour;
import ru.itmo.utils.HibernateSessionFactory;

import java.util.List;

public class KittyDao implements IKittyDao {
    @Override
    public void add(Kitty kitty) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(kitty);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(Kitty kitty) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(kitty);
        transaction.commit();
        session.close();
    }

    @Override
    public boolean removeById(Long id) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Kitty kitty = session.get(Kitty.class, id);
        if (kitty == null) {
            transaction.commit();
            session.close();
            return false;
        }
        session.remove(kitty);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public Owner getOwner(Long kittyId) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Kitty kitty = session.get(Kitty.class, kittyId);
        Owner owner = kitty.getOwner();
        session.close();
        return owner;
    }

    @Override
    public Kitty getById(Long id) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Kitty kitty = session.get(Kitty.class, id);
        session.close();
        return kitty;
    }

    @Override
    public Kitty getByName(String name) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Kitty kitty = session.createQuery("FROM Kitty k WHERE k.name=:name", Kitty.class)
                .setParameter("name", name)
                .uniqueResult();
        session.close();
        return kitty;
    }

    @Override
    public List<Kitty> getAll() {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        List<Kitty> kitties = session.createQuery("FROM Kitty", Kitty.class).list();
        session.close();
        return kitties;
    }

    public List<Kitty> getAllFriends(Long id) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        List<Kitty> friends = session.createQuery("SELECT k.friends FROM Kitty k WHERE k.id=:id", Kitty.class)
                .setParameter("id", id)
                .list();
        session.close();
        return friends;
    }

    @Override
    public List<Kitty> findAllWithBreed(Breed breed) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        List<Kitty> kitties = session.createQuery("FROM Kitty k WHERE k.breed=:breed", Kitty.class)
                .setParameter("breed", breed)
                .list();
        session.close();
        return kitties;
    }

    @Override
    public List<Kitty> findAllWithColor(Colour colour) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        List<Kitty> kitties = session.createQuery("FROM Kitty k WHERE k.colour=:colour", Kitty.class)
                .setParameter("colour", colour)
                .list();
        session.close();
        return kitties;
    }
}
