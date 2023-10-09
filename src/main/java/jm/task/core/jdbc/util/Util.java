package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Util {
    public static Session getSessionFactory() {
        SessionFactory sessionFactory = new Configuration().addAnnotatedClass(User.class).buildSessionFactory();
        return sessionFactory.openSession();
    }
}
