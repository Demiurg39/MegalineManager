package org.megaline.core.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.megaline.core.models.Question;
import org.megaline.services.HibernateSessionFactoryUtil;

import java.util.List;

public class QuestionDao {

    private final SessionFactory sessionFactory;

    public QuestionDao() {
        this.sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
    }

    public boolean saveOrUpdate(Question question) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(question);
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

    public Question findById(long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Question.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Question> searchQuestionsAndAnswers(String query) {
        try (Session session = sessionFactory.openSession()) {
            // Используем HQL для поиска вопросов, содержащих введенный запрос
            Query<Question> searchQuery = session.createQuery(
                    "SELECT DISTINCT q FROM Question q LEFT JOIN FETCH q.answers a WHERE q.question LIKE :query OR a.answer LIKE :query",
                    Question.class
            );
            searchQuery.setParameter("query", "%" + query + "%");
            return searchQuery.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Question> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Question", Question.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void delete(Question question) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(question);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}