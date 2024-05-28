package org.megaline.core.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.megaline.core.models.TariffPlan;
import org.megaline.services.HibernateSessionFactoryUtil;

import java.util.List;

public class TariffPlanDao {

    private final SessionFactory sessionFactory;

    public TariffPlanDao() {
        this.sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
    }

    public void saveOrUpdate(TariffPlan tariffPlan) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(tariffPlan);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public TariffPlan findById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(TariffPlan.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<TariffPlan> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM TariffPlan", TariffPlan.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void delete(TariffPlan tariffPlan) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(tariffPlan);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
