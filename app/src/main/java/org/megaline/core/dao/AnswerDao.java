package org.megaline.core.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.megaline.core.models.Answer;
import org.megaline.services.HibernateSessionFactoryUtil;

import java.util.List;

public class AnswerDao {

    private final SessionFactory sessionFactory;

    public AnswerDao() {
        this.sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
    }

    public boolean saveOrUpdate(Answer answer) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(answer);
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

    public Answer findById(long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Answer.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Answer> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Answer", Answer.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void delete(Answer answer) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(answer);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}