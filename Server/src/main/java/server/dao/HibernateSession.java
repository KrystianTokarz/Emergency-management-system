package server.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * HibernateSession class (singleton) with EntityManagerFactory
 */
public class HibernateSession {

    private static HibernateSession instance = null;

    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("systemDatabase");

    private static EntityManager entityManager;

    private HibernateSession() {
    }

    public static HibernateSession getInstance() {
        if (instance == null) {
            instance = new HibernateSession();
        }
        return instance;
    }

    public EntityManager getEntityManager(){
        if(entityManager == null)
            entityManager = entityManagerFactory.createEntityManager();
        return entityManager;
    }

    public void closeEntityManagerFactory(){
        if(entityManagerFactory != null) {
            entityManagerFactory.close();
            entityManagerFactory = null;
        }
    }
}
