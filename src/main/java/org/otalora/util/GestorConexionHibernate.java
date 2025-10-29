package org.otalora.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class GestorConexionHibernate {

    private final SessionFactory sessionFactory;

    public GestorConexionHibernate() {
        try {
            Configuration confi = new Configuration();
            confi.configure("hibernate.cfg.xml");
            this.sessionFactory = confi.buildSessionFactory();
            
        } catch (Throwable ex) {
            System.err.println("Error al inicializar SessionFactory: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void shutdown() {
        sessionFactory.close();
    }
}
