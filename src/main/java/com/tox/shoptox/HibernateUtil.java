package com.tox.shoptox;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
     private static SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory(){

        if (sessionFactory == null) {
            try {
                // A SessionFactory is set up once for an application!
                final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                        .configure("hibernate.cfg.xml") // configures settings from hibernate.cfg.xml
                        .build();
                try {
                    sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
                }
                catch (Exception e) {
                    // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
                    // so destroy it manually.
                    StandardServiceRegistryBuilder.destroy( registry );
                }
            }
            catch (Throwable ex) {
                // Make sure you log the exception, as it might be swallowed
                System.err.println("Initial SessionFactory creation failed." + ex);
                throw new ExceptionInInitializerError(ex);
            }
        }
        return sessionFactory;
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}
