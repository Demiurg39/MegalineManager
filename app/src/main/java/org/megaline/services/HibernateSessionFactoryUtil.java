package org.megaline.services;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.megaline.core.models.*;


public class HibernateSessionFactoryUtil {
  private static SessionFactory sessionFactory;

  private HibernateSessionFactoryUtil() {}

  public static SessionFactory getSessionFactory() {
    if (sessionFactory == null) {
      try {
        Configuration configuration = new Configuration().configure();
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Connection.class);
        configuration.addAnnotatedClass(TariffPlan.class);
        configuration.addAnnotatedClass(Employee.class);
        configuration.addAnnotatedClass(Question.class);
        configuration.addAnnotatedClass(Answer.class);
        configuration.addAnnotatedClass(Ticket.class);
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(builder.build());

      } catch (Exception e) {
        System.out.println("Something went wrong : " + e);
      }
    }
    return sessionFactory;
  }
}
