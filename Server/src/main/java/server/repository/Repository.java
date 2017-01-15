package server.repository;

import server.dao.HibernateSession;
import server.model.employee.Employee;
import server.model.institution.Institution;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;

/**
 * Abstract class with HibernateSession class for other Repository with extends this abstract class
 */
public abstract class Repository {

    private HibernateSession session;
    protected EntityManager entityManager;

    public Repository(){
        session = HibernateSession.getInstance();
        entityManager = session.getEntityManager();
}
    public abstract  <T> T findById(Long id);




//    public abstract <T> List<T> findAll();


}
