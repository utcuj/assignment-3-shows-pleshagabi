package session.factory;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.logging.Level;

/**
 * Created by plesha on 09-May-18.
 */
public class MySessionFactory {
    public static SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration().configure();

        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());

        SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());

        return sessionFactory;
    }
}
