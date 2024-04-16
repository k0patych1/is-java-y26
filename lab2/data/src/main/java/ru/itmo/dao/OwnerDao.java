package ru.itmo.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.itmo.entity.Kitty;
import ru.itmo.entity.Owner;
import ru.itmo.utils.HibernateSessionFactory;

import java.util.List;

public class OwnerDao implements IOwnerDao {
    @Override
    public void add(Owner owner) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(owner);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(Owner owner) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(owner);
        transaction.commit();
        session.close();
    }

    @Override
    public void addKittyToOwner(Long kittyId, Long ownerId) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Owner owner = session.get(Owner.class, ownerId);
        Kitty kitty = session.get(Kitty.class, kittyId);
        owner.getKitties().add(kitty);
        session.merge(owner);
        transaction.commit();
        session.close();
    }

    @Override
    public boolean removeById(Long id) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Owner owner = session.get(Owner.class, id);
        if (owner == null) {
            transaction.commit();
            session.close();
            return false;
        }
        session.remove(owner);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public Owner getById(Long id) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Owner owner = session.get(Owner.class, id);
        session.close();
        return owner;
    }

    @Override
    public List<Owner> getAll() {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        List<Owner> owners = session.createQuery("FROM Owner", Owner.class).list();
        session.close();
        return owners;
    }

    @Override
    public List<Kitty> getAllKitties(Long id) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        List<Kitty> kitties = session.createQuery("SELECT o.kitties FROM Owner o WHERE o.id=:id", Kitty.class)
                .setParameter("id", id)
                .list();
        session.close();
        return kitties;
    }
}
