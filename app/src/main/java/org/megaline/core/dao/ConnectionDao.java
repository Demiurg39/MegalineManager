package org.megaline.core.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.megaline.core.models.Connection;
import org.megaline.core.models.User;
import org.megaline.services.HibernateSessionFactoryUtil;

import java.util.List;

public class ConnectionDao {

    private final SessionFactory sessionFactory;

    public ConnectionDao() {
        this.sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
    }

    public boolean saveOrUpdate(Connection connection) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(connection);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    public Connection findById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Connection.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Connection findByUserId(Long userId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Connection> query = session.createQuery("FROM Connection WHERE user.id = :userId", Connection.class);
            query.setParameter("userId", userId);
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Connection> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Connection", Connection.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void delete(Connection connection) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(connection);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
